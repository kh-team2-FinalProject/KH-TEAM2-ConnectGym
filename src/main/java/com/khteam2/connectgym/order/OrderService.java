package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.CommonUtil;
import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.order.dto.OrderCompleteResponseDto;
import com.khteam2.connectgym.order.dto.OrderProcessRequestDto;
import com.khteam2.connectgym.order.dto.OrderProcessResponseDto;
import com.khteam2.connectgym.order.dto.OrderResponseDto;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private IamportClient iamportClient;
    private final Random random = new Random();

    /**
     * 결제 진행 전 실행되는 메소드
     *
     * @param loginMemberNo 로그인되어 있는 회원의 no
     * @param lessonNolist  lesson 번호가 담겨있는 List
     */
    @Transactional(readOnly = true)
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

        long totalPrice = 0L;
        for (Lesson lesson : lessons) {
            // 가져온 강의의 금액을 모두 더한다.
            totalPrice += lesson.getPrice();
        }

        // 주문 번호를 생성한다.
        String merchantUid = this.generateOrderNo();

        // 결제 전 포트원 서버로 전송할 객체 선언. 주문 번호와 결제할 금액을 넘긴다.
        PrepareData prepareData = new PrepareData(merchantUid, BigDecimal.valueOf(totalPrice));
        // 포트원 서버로 사전 검증 요청을 전송하고 받아온다.
        IamportResponse<Prepare> postPrepareResponse = null;
        try {
            postPrepareResponse = this.iamportClient.postPrepare(prepareData);
        } catch (IOException | IamportResponseException e) {
            log.error(e.getMessage(), e);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }

        if (postPrepareResponse.getCode() != 0) {
            responseDto.setMessage(postPrepareResponse.getMessage());
            return responseDto;
        }

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
        boolean result = false;
        // 해당 회원이 신청한 레슨을 모두 가져온다.
        List<Lesson> alreadyLessonList = this.lessonRepository.findByMemberNo(memberNo);

        // 레슨 리스트를 순환해서 해당 번호가 존재하면 true로 설정한 뒤 반복문에서 빠져나온다.
        for (Lesson lesson : alreadyLessonList) {
            if (lessonNoList.contains(lesson.getNo())) {
                result = true;
                break;
            }
        }

        return result;
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

        for (int i = 0; i < 10; i++) {
            // 1 ~ 999,999 값을 생성함
            int randomValue = CommonUtil.generateRandomNumberInt(6);
            // 주문 번호 형식: 20230820001024 / 20230820123456
            orderNo = String.format("%s%06d", date, randomValue);

            // 해당 주문 번호가 있는지 확인
            Order order = this.orderRepository.findById(orderNo).orElse(null);
            if (order == null) {
                // 해당 주문 번호가 없으면 반복문에서 빠져 나옴
                break;
            }
        }

        return orderNo;
    }

    /**
     * 주문 진행 중일 때 실행되는 메소드
     */
    @Transactional
    public OrderProcessResponseDto processOrder(OrderProcessRequestDto requestDto) {
        OrderProcessResponseDto returnDto = OrderProcessResponseDto.builder()
            .success(false)
            .url("/order/fail")
            .build();

        Long loginMemberNo = requestDto.getSLoginMemberNo();
        String merchantUid = requestDto.getSMerchantUid();
        Long totalPrice = requestDto.getSTotalPrice();
        List<Long> lessonNolist = requestDto.getSLessonNolist();
        boolean isApi = requestDto.isApi();
        String impUid = requestDto.getImpUid();
        String errorMsg = requestDto.getErrorMsg();

        if (loginMemberNo == null) {
            returnDto.setMessage("로그인되어 있지 않습니다.");
            return returnDto;
        }

        // 레슨 번호가 들어있는 리스트를 통해서 DB에서 레슨 목록을 받아온다.
        List<Lesson> lessonList = this.lessonRepository.findAllById(lessonNolist);

        // 레슨 번호 리스트와 DB에서 가져온 레슨의 개수를 비교해서 다르면 실패 메시지를 반환한다.
        if (lessonNolist.size() != lessonList.size()) {
            log.error("가져온 lesson과 lessonNo 리스트의 사이즈가 다릅니다");
            returnDto.setMessage("내부 서버 문제로 인해 결제에 실패했습니다.");
            return returnDto;
        }

        // 로그인되어 있는 사용자 ID를 이용해서 사용자 정보를 DB에서 꺼내온다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        if (member == null) {
            log.error("DB에서 사용자 정보를 찾을 수 없습니다.");
            returnDto.setMessage("사용자 정보를 찾을 수 없습니다.");
            return returnDto;
        }

        Payment payment = null;

        try {
            // 포트원 서버로 주문 번호를 전송해 해당 결제건을 받아온다.
            payment = this.iamportClient.paymentByImpUid(impUid).getResponse();
        } catch (IamportResponseException e) {
            returnDto.setMessage("결제에 실패했습니다. 서버 메시지: " + errorMsg);
            log.error("포트원 서버에서 오류가 발생했습니다.", e);
            return returnDto;
        } catch (IOException e) {
            returnDto.setMessage("결제 서버에 연결하지 못 했습니다. " + errorMsg);
            log.error("포트원 서버에 연결하는 중 문제가 발생했습니다.", e);
            return returnDto;
        }

        // 포트원 서버에서 가져온 결제건과 요청한 결제건이 일치하지 않거나 금액이 일치하지 않으면 실패 메시지를 반환한다.
        if (!payment.getMerchantUid().equals(merchantUid)
            || !payment.getAmount().equals(BigDecimal.valueOf(totalPrice))) {
            returnDto.setMessage("검증 실패");
            return returnDto;
        }

        // DTO에 URL을 설정해 준다.
        String tempUrl = "/order/complete?orderId=" + merchantUid;
        returnDto.setUrl(isApi ? tempUrl : "redirect:" + tempUrl);

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
                .build()
            )
            .collect(Collectors.toList());

        // order_detail 테이블에 주문한 강의를 모두 넣는다.
        List<OrderDetail> savedOrderDetailList = this.orderDetailRepository.saveAll(newOrderDetailList);

        returnDto.setSuccess(true);

        return returnDto;
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
            .url("/content/orderComplete")
            .build();

        Order order = this.orderRepository.findById(orderId).orElse(null);
        List<OrderDetail> orderDetailList = this.orderDetailRepository.findByOrder(order);

        return responseDto;
    }
}
