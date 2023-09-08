package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.CommonUtil;
import com.khteam2.connectgym.common.Pagination;
import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.order.dto.*;
import com.khteam2.connectgym.review.ReviewRepository;
import com.khteam2.connectgym.trainer.Trainer;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderProgressRepository orderProgressRepository;
    private final OrderProgressDetailRepository orderProgressDetailRepository;
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final IamportClient iamportClient;
    private final ReviewRepository reviewRepository;

    /**
     * 결제 진행 전 실행되는 메소드
     *
     * @param loginMemberNo 로그인되어 있는 회원의 no
     * @param lessonNolist  lesson 번호가 담겨있는 List
     */
    @Transactional
    public OrderResponseDto prepareOrder(Long loginMemberNo, List<Long> lessonNolist) {
        OrderResponseDto responseDto = OrderResponseDto.builder()
            .success(false)
            .build();

        // 레슨 번호 리스트가 없는 상황을 위해 예외를 처리한다.
        if (lessonNolist == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        // 회원 정보를 조회한다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        if (member == null) {
            responseDto.setMessage("해당 회원을 찾을 수 없습니다.");
            return responseDto;
        }

        // DB에서 해당 강의 리스트를 가져온다.
        List<Lesson> lessons = this.lessonRepository.findAllById(lessonNolist);

        // 해당 강의 리스트가 존재하지 않거나 크기가 일치하지 않으면 객체를 반환한다.
        if (lessons.isEmpty() || lessonNolist.size() != lessons.size()) {
            responseDto.setMessage("존재하지 않는 레슨이 포함되어 있어 진행할 수 없습니다.");
            return responseDto;
        }

        // 해당 강의를 결제했는지 확인한다.
        boolean lessonResult = this.hasLesson(member.getNo(), lessonNolist);

        // 결제한 내역이 존재하면 거부 메시지를 설정한 후 객체를 반환한다.
        if (lessonResult) {
            responseDto.setMessage("이미 결제한 강의가 포함되어 있어 결제할 수 없습니다.");
            return responseDto;
        }

        LocalDate localDateToday = LocalDate.now();

        long totalPrice = 0L;
        for (Lesson lesson : lessons) {
            // 강의를 시작한 이후인지 확인하고 객체를 반환한다.
            if (localDateToday.isAfter(lesson.getStart_date())) {
                responseDto.setMessage("이미 시작했거나 종료된 강의가 포함되어 있어 결제할 수 없습니다.");
                return responseDto;
            }

            // 가져온 강의의 금액을 모두 더한다.
            totalPrice += lesson.getPrice();
        }

        // 주문 번호를 생성한다.
        String merchantUid = this.generateOrderNo();

        // 포트원 서버로 사전 검증 요청을 전송하고 받아온다.
        IamportResponse<Prepare> postPrepareResponse = null;
        try {
            postPrepareResponse = this.preparePortOne(merchantUid, totalPrice);
        } catch (IamportResponseException e) {
            log.error(e.getMessage(), e);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }

        // 정상적으로 되지 않았을 경우 객체를 반환한다.
        if (postPrepareResponse.getCode() != 0) {
            responseDto.setMessage(postPrepareResponse.getMessage());
            return responseDto;
        }

        // 결제할 금액과 주문 번호를 저장하기 위한 객체를 생성한다.
        OrderProgressDto orderProgressDto = OrderProgressDto.builder()
            .orderNo(merchantUid)
            .price(totalPrice)
            .build();

        // 결제할 금액과 주문 번호를 저장한다.
        OrderProgress orderProgress = this.orderProgressRepository.save(orderProgressDto.toEntity());

        // 결제할 레슨 등의 상세 정보를 저장하기 위해서 리스트를 생성한다.
        List<OrderProgressDetail> orderProgressDetailList = lessons.stream()
            .map(lesson -> OrderProgressDetail.builder()
                .orderProgress(orderProgress)
                .lesson(lesson)
                .build())
            .collect(Collectors.toList());

        // 결제 예정 상세 정보를 모두 DB에 저장한다.
        this.orderProgressDetailRepository.saveAll(orderProgressDetailList);

        // View로 넘겨줄 값들을 DTO에 넘겨준다.
        responseDto.setSuccess(true);
        responseDto.setLessonList(lessons);
        responseDto.setName(member.getUserName());
        responseDto.setTelNo(member.getUserTel());
        responseDto.setPrice(totalPrice);
        responseDto.setOrderNo(merchantUid);

        return responseDto;
    }

    /**
     * 해당 회원이 신청한 레슨이 있는지 확인하는 메소드
     *
     * @param memberNo     회원 번호
     * @param lessonNoList 레슨 번호가 들어있는 리스트
     * @return 회원이 신청한 레슨이 존재하면 {@code true}, 아니면 {@code false} 반환
     */
    @Transactional(readOnly = true)
    private boolean hasLesson(Long memberNo, List<Long> lessonNoList) {
        // 해당 회원이 신청한 레슨을 모두 가져온다.
        List<Lesson> alreadyLessonList = this.lessonRepository.findAllByMemberNoAndLessonNoList(memberNo, lessonNoList);

        return !alreadyLessonList.isEmpty();
    }

    /**
     * 주문 번호 생성 메소드.
     * 생성한 주문 번호를 DB에서 조회해서 만약 존재하면 새로운 주문 번호를 생성한다.
     *
     * @return 사용되지 않은 새 주문 번호
     */
    @Transactional(readOnly = true)
    private String generateOrderNo() {
        String orderNo = null;
        String date = CommonUtil.getTodayLocalDate8(); // 20230820

        for (int i = 0; i < 100_000; i++) {
            // 1 ~ 999,999 값을 생성한다.
            int randomValue = CommonUtil.generateRandomNumberInt(6);
            // 주문 번호 형식: 20230820001024 / 20230820123456
            orderNo = String.format("%s%06d", date, randomValue);

            // 해당 주문 번호가 있는지 확인한다.
            OrderProgress orderProgress = this.orderProgressRepository.findByOrderNo(orderNo);

            if (orderProgress == null) {
                // 해당 주문 번호가 없으면 반복문에서 빠져 나온다.
                break;
            }
        }

        return orderNo;
    }

    /**
     * 결제 전 포트원 서버에 사전 검증할 때 사용하는 메소드.
     * 사전 검증을 하는 이유는 클라이언트 측에서 결제 금액을 조작할 가능성이 있기 때문에
     * 결제하기 전 포트원 서버에 "얼마만큼 결제할 것이다"라고 알려준다.
     *
     * @param merchantUid 주문 번호
     * @param totalPrice  결제할 금액
     * @return 포트원 서버에서 가져온 사전 검증 결과 반환
     * @throws IamportResponseException 요청을 보냈으나 포트원 서버에서 오류가 발생할 경우 예외 발생
     */
    @Transactional
    public IamportResponse<Prepare> preparePortOne(String merchantUid, Long totalPrice)
        throws IamportResponseException {
        // 결제 전 포트원 서버로 전송할 객체 선언. 주문 번호와 결제할 금액을 넘긴다.
        PrepareData prepareData = new PrepareData(merchantUid, BigDecimal.valueOf(totalPrice));

        IamportResponse<Prepare> postPrepareResponse = null;
        try {
            // 포트원 서버로 사전 검증 요청을 전송하고 받아온다.
            postPrepareResponse = this.iamportClient.postPrepare(prepareData);
        } catch (IOException e) {
            // 포트원 서버에 요청하지 못 했을 경우 IOException 예외가 발생한다.
            log.error(e.getMessage(), e);
        }

        // 포트원 서버에서 받은 객체를 반환하거나 null을 반환한다.
        return postPrepareResponse;
    }

    /**
     * 주문 진행 중일 때 실행되는 메소드
     *
     * @param requestDto     요청받은 DTO
     * @param sLoginMemberNo 세션에 저장된 로그인된 멤버 번호
     * @param isApi          API 이용 여부
     * @return 응답 객체 반환
     */
    @Transactional
    public OrderProcessResponseDto processOrder(
        OrderProcessRequestDto requestDto, Long sLoginMemberNo, boolean isApi) {
        OrderProcessResponseDto responseDto = OrderProcessResponseDto.builder()
            .success(false)
            .url("/order/fail")
            .build();

        String impUid = requestDto.getImp_uid();
        String errorMsg = requestDto.getError_msg();

        // 로그인되어 있지 않으면 객체를 리턴한다.
        if (sLoginMemberNo == null) {
            responseDto.setMessage("로그인되어 있지 않습니다.");
            return responseDto;
        }

        // 로그인되어 있는 사용자 ID를 이용해서 사용자 정보를 DB에서 꺼내온다.
        Member member = this.memberRepository.findById(sLoginMemberNo).orElse(null);

        // 사용자 정보를 찾을 수 없으면 객체를 반환한다.
        if (member == null) {
            log.error("DB에서 사용자 정보를 찾을 수 없습니다.");
            responseDto.setMessage("사용자 정보를 찾을 수 없습니다.");
            return responseDto;
        }

        // 포트원 결제 번호가 없으면 객체를 반환한다.
        if (impUid == null || impUid.isEmpty()) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        IamportResponse<Payment> response = null;

        try {
            // 포트원 서버로 주문 번호를 전송해 해당 결제건을 받아온다.
            response = this.iamportClient.paymentByImpUid(impUid);
        } catch (IamportResponseException e) {
            responseDto.setMessage("결제에 실패했습니다. 서버 메시지: " + errorMsg);
            log.error("포트원 서버에서 오류가 발생했습니다.", e);
            return responseDto;
        } catch (IOException e) {
            responseDto.setMessage("결제 서버에 연결하지 못 했습니다. " + errorMsg);
            log.error("포트원 서버에 연결하는 중 문제가 발생했습니다.", e);
            return responseDto;
        }

        Payment payment = response.getResponse();
        String merchantUid = payment.getMerchantUid();

        // 결제 주문건을 DB에서 불러온다.
        OrderProgress orderProgress = this.orderProgressRepository.findByOrderNo(merchantUid);

        if (orderProgress == null) {
            responseDto.setMessage("해당 주문건이 없습니다.");
            return responseDto;
        }

        // 전체 금액을 꺼내온다.
        Long totalPrice = orderProgress.getPrice();

        // 결제 결과 검증 메소드를 통해 검증에 실패했으면 실패 메시지를 반환한다.
        if (!this.verifyPortonePayment(response, merchantUid, totalPrice)) {
            responseDto.setMessage("검증 실패");
            return responseDto;
        }

        // 결제 예정 항목의 주문 번호를 이용해서 레슨 리스트를 가져온다.
        List<Lesson> lessonList = this.lessonRepository.findAllByOrderProgressOrderNo(merchantUid);

        // DTO에 URL을 설정해 준다.
        responseDto.setUrl("/order/complete?orderId=" + merchantUid);

        // DB에 주문을 저장하기 위해 객체를 생성한다.
        Order newOrder = Order.builder()
            .no(payment.getMerchantUid())
            .member(member)
            .orderPay(totalPrice)
            .dayOfPayment(Timestamp.valueOf(LocalDateTime.now()))
            .type(payment.getPayMethod())
            .build();

        // 주문을 DB에 넣는다.
        Order savedOrder = this.orderRepository.save(newOrder);

        // order_detail 테이블에 저장할 리스트를 생성한다.
        List<OrderDetail> newOrderDetailList = lessonList.stream()
            .map(lesson -> OrderDetail.builder()
                .lesson(lesson)
                .enrollKey(this.generateEnrollKey())
                .order(savedOrder)
                .build())
            .collect(Collectors.toList());

        // order_detail 테이블에 주문한 강의를 모두 넣는다.
        List<OrderDetail> savedOrderDetailList = this.orderDetailRepository.saveAll(newOrderDetailList);

        responseDto.setSuccess(true);

        return responseDto;
    }

    /**
     * 새로운 enroll_key를 생성하는 메소드
     *
     * @return 중복되지 않는 고유한 값
     */
    private long generateEnrollKey() {
        long enrollKey = -1;

        for (int i = 0; i < 10_000; i++) {
            enrollKey = CommonUtil.generateRandomNumberLong(12);
            OrderDetail orderDetail = this.orderDetailRepository.findByEnrollKey(enrollKey);

            if (orderDetail == null) {
                break;
            }
        }

        return enrollKey;
    }

    /**
     * 결제 결과 검증 메소드
     *
     * @param response    포트원 서버에서 받아온 객체
     * @param merchantUid 검증할 주문 번호
     * @param totalPrice  결제 금액
     * @return 포트원 서버에서 가져온 결제건과 요청한 결제건이 일치하고 금액이 일치하면 {@code true} 반환.
     * 하나라도 일치하지 않으면 {@code false} 반환
     */
    public boolean verifyPortonePayment(IamportResponse<Payment> response, String merchantUid, long totalPrice) {
        Payment payment = response.getResponse();

        return payment != null && payment.getMerchantUid().equals(merchantUid)
            && payment.getAmount().equals(BigDecimal.valueOf(totalPrice));
    }

    public ResponseEntity<Object> paymentCompleteWebhook(String impUid, String sMerchantUid, Long sPrice) {
        Payment payment = null;

        try {
            payment = this.iamportClient.paymentByImpUid(impUid).getResponse();
        } catch (IamportResponseException e) {
            log.error("아임포트 webhook error: ", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("아임포트 서버 접속 실패: ", e);
            throw new RuntimeException(e);
        }

        if (payment.getMerchantUid().equals(sMerchantUid) && payment.getAmount().equals(BigDecimal.valueOf(sPrice))) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @Transactional(readOnly = true)
    public OrderCompleteResponseDto completeOrder(Long loginMemberId, String orderId) {
        OrderCompleteResponseDto responseDto = OrderCompleteResponseDto.builder()
            .success(false)
            .url("content/orderComplete")
            .build();

        Order order = this.orderRepository.findById(orderId).orElse(null);
        List<OrderDetail> orderDetailList = this.orderDetailRepository.findByOrder(order);

        return responseDto;
    }

    /**
     * 주문 내역을 가져오는 메소드
     *
     * @param loginMemberNo       로그인된 멤버 번호
     * @param orderListRequestDto 사용자가 요청한 정보
     * @return 성공/실패, 주문건에 대한 정보 등을 담은 객체 반환
     */
    @Transactional(readOnly = true)
    public OrderListResponseDto orderList(Long loginMemberNo, OrderListRequestDto orderListRequestDto) {
        OrderListResponseDto responseDto = OrderListResponseDto.builder()
            .success(false)
            .build();

        // 로그인된 사용자가 없으면 객체를 반환한다.
        if (loginMemberNo == null) {
            responseDto.setMessage("로그인한 상태가 아닙니다.");
            return responseDto;
        }

        // 멤버 번호를 이용해서 멤버를 찾는다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        // 해당 멤버가 없으면 객체를 반환한다.
        if (member == null) {
            responseDto.setMessage("사용자 정보가 없습니다.");
            return responseDto;
        }

        int pageSize = 5;
        int currentPage = 0;

        if (orderListRequestDto.getSize() != null) {
            pageSize = orderListRequestDto.getSize();
        }

        if (orderListRequestDto.getPage() != null) {
            currentPage = orderListRequestDto.getPage() - 1;
        }

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Order> orderList = null;

        // 주문건을 역순으로 가져온다.
        if (orderListRequestDto.getStartDate() != null && orderListRequestDto.getEndDate() != null) {
            // 사용자 정보와 시작일, 종료일을 이용해서 그에 해당하는 주문건을 주문일 역순으로 가져온다.
            orderList = this.orderRepository.findByMemberAndDayOfPaymentBetweenOrderByDayOfPaymentDesc(
                member,
                Date.valueOf(orderListRequestDto.getStartDate()),
                Date.valueOf(orderListRequestDto.getEndDate()),
                pageable
            );
        } else {
            // 시작일 또는 종료일이 존재하지 않을 경우 모든 내용을 가져온다.
            orderList = this.orderRepository.findByMemberOrderByDayOfPaymentDesc(member, pageable);
        }

        // 가져온 주문건을 화면에 출력하기 위해서 List 타입의 객체를 만들어준다.
        List<OrderListOrderDto> orderListOrderDtoList = new ArrayList<>();
        //        List<OrderDetail> orderDetailListAll = this.orderDetailRepository.findByMemberNo(member.getNo());

        for (Order order : orderList) {
            // 전체 금액을 넣는다.
            long totalPrice = 0;

            // 주문건을 이용해서 주문 상세 정보를 가져온다.
            List<OrderDetail> orderDetailList = null;
            String searchString = orderListRequestDto.getQ();

            // 검색어가 존재하는지 확인한다.
            if (searchString != null && !searchString.isBlank()) {
                // 검색어를 이용해서 주문 상세건을 가져온다.
                orderDetailList = this.orderDetailRepository.findByLessonTitleOrTrainerName(searchString);
            } else {
                // 검색어가 없으면 모든 주문 상세건을 가져온다.
                orderDetailList = this.orderDetailRepository.findByOrder(order);
            }

            // 화면에 출력할 상세 정보들을 담아주기 위해서 새 List 객체를 만든다.
            List<OrderListOrderDetailDto> detailDtoList = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetailList) {
                // 가져온 주문 상세 정보에서 레슨을 가져온다.
                Lesson lesson = orderDetail.getLesson();
                // 레슨에서 트레이너 정보를 가져온다.
                Trainer trainer = lesson.getTrainer();

                // 전체 금액에 레슨 가격을 더 한다.
                totalPrice += lesson.getPrice();

                // 레슨의 시작일과 종료일을 가져온다.
                LocalDate startDate = lesson.getStart_date();
                LocalDate endDate = lesson.getEnd_date();
                // 현재 날짜를 가져온다.
                LocalDate localDateToday = LocalDate.now();
                String status = null;

                // 오늘 날짜와 강의 날짜를 비교해서 그에 맞는 조건문을 실행한다.
                if (localDateToday.isBefore(startDate)) {
                    // 강의를 수강하기 전이면 실행되는 조건문
                    status = "결제 완료";
                } else if (localDateToday.isAfter(startDate) && localDateToday.isBefore(endDate)) {
                    // 강의를 수강하는 중일 때 실행되는 조건문
                    status = "수강 중";
                } else if (localDateToday.isAfter(endDate)) {
                    // 수강할 수 있는 날짜가 지났을 때 실행되는 조건문
                    status = "수강 완료";
                }

                //리뷰 작성 여부
                boolean reviewYn;

                if (reviewRepository.findOrderDetailNo(orderDetail.getNo()).orElse(null) == null) {
                    reviewYn = true;
                } else {
                    reviewYn = false;
                }


                // 상세 정보 DTO를 생성해서 가져온 정보들을 담아준다.
                OrderListOrderDetailDto detailDto = OrderListOrderDetailDto.builder()
                    .orderDetailNo(orderDetail.getNo())
                    .reviewYn(reviewYn)
                    .title(lesson.getTitle())
                    .startDate(lesson.getStart_date())
                    .endDate(lesson.getEnd_date())
                    .price((long) lesson.getPrice())
                    .trainerName(trainer.getTrainerName())
                    .imageUrl(lesson.getLesson_img())
                    .lessonNo(lesson.getNo())
                    .status(status)
                    .build();
                // 상세 정보 리스트에 담는다.
                detailDtoList.add(detailDto);
            }

            // 상세 정보 리스트가 비어있지 않을 때에만 목록에 담아준다.
            if (!detailDtoList.isEmpty()) {
                // 주문 DTO를 생성해서 가져온 정보들을 담아준다.
                OrderListOrderDto orderDto = OrderListOrderDto.builder()
                    .orderNo(order.getNo())
                    .orderDate(order.getDayOfPayment().toLocalDateTime())
                    .detailDtoList(detailDtoList)
                    .totalPrice(totalPrice)
                    .build();

                // 주문 DTO 리스트에 담는다.
                orderListOrderDtoList.add(orderDto);
            }
        }

        Pagination pagination = new Pagination(orderList, 5);

        // 가져온 정보들을 담아준다.
        responseDto.setSearch(orderListRequestDto.getQ());
        responseDto.setStartDate(orderListRequestDto.getStartDate());
        responseDto.setEndDate(orderListRequestDto.getEndDate());
        responseDto.setStatus(orderListRequestDto.getStatus());
        responseDto.setOrderListOrderDtoList(orderListOrderDtoList);
        responseDto.setPagination(pagination);
        responseDto.setSuccess(true);

        return responseDto;
    }
}
