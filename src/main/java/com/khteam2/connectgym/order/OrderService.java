package com.khteam2.connectgym.order;

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
import java.time.format.DateTimeFormatter;
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
    public OrderResponseDto prepareOrder(Long loginMemberNo, List<Long> lessonNolist) throws IamportResponseException, IOException {
        OrderResponseDto responseDto = OrderResponseDto.builder()
            .success(false)
            .build();

        // DB에서 해당 강의 리스트를 가져온다.
        List<Lesson> lessons = this.lessonRepository.findAllById(lessonNolist);

        if (lessons.isEmpty()) {
            responseDto.setMessage("잘못된 요청입니다.");
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
        IamportResponse<Prepare> postPrepareResponse = this.iamportClient.postPrepare(prepareData);

        // if (postPrepareResponse.getCode() != 0) {}

        // 회원 정보를 조회한다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

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
     * 주문 번호 생성 메소드.
     * 생성한 주문 번호를 DB에서 조회해서 만약 존재하면 새로운 주문 번호를 생성한다.
     *
     * @return 사용되지 않은 새 주문 번호
     */
    @Transactional
    private String generateOrderNo() {
        String orderNo = null;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 20230820

        for (int i = 0; i < 10; i++) {
            // 1 ~ 999,999 값을 생성함
            int randomValue = random.nextInt(999_999) + 1;
            // 주문 번호 형식: 20230820001024 / 20230820123456
            orderNo = String.format("%s%06d", date, randomValue);

            // 해당 주문 번호가 있는지 확인
            Order order = this.orderRepository.findById(orderNo).orElse(null);
            if (order == null) {
                // 해당 주문 번호가 없으면 반복문에서 빠져 나옴
                break;
            }
        }

        return orderNo; // this.orderRepository.getMaxNo() + 1;
    }

    /**
     * 주문 진행 중일 때 실행되는 메소드
     */
    @Transactional
    public OrderProcessResponseDto processOrder(
        OrderProcessRequestDto requestDto
    ) {
        Long loginMemberNo = requestDto.getSLoginMemberNo();
        String merchantUid = requestDto.getSMerchantUid();
        Long totalPrice = requestDto.getSTotalPrice();
        List<Long> lessonNolist = requestDto.getSLessonNolist();
        boolean isPC = requestDto.isPC();
        String impUid = requestDto.getImpUid();
        String errorMsg = requestDto.getErrorMsg();

        OrderProcessResponseDto returnDto = OrderProcessResponseDto.builder()
            .success(false)
            .build();

        if (!isPC && loginMemberNo == null) {
            returnDto.setMessage("로그인되어 있지 않습니다.");
            return returnDto;
        }

        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);
        Payment payment = null;
        String message = null;
        String url = "/order/fail";

        try {
            payment = this.iamportClient.paymentByImpUid(impUid).getResponse();
        } catch (IamportResponseException e) {
            message = "결제에 실패했습니다. 서버 메시지: " + errorMsg;
            log.error("포트원 서버에서 오류가 발생했습니다.", e);
        } catch (IOException e) {
            message = "결제 서버에 연결하지 못 했습니다. 서버 메시지: " + errorMsg;
            log.error("포트원 서버에 연결하는 중 문제가 발생했습니다.", e);
        }

        if (payment != null) {
            if (payment.getMerchantUid().equals(merchantUid)
                && payment.getAmount().equals(BigDecimal.valueOf(totalPrice))
            ) {
                returnDto.setSuccess(true);
                String tempUrl = "/order/complete?orderId=" + merchantUid;
                url = isPC ? tempUrl : "redirect:" + tempUrl;

                List<Lesson> lessonList = this.lessonRepository.findAllById(lessonNolist);

                if (lessonNolist.size() == lessonList.size()) {
                    // DB에 저장
                    Order order = Order.builder()
                        .no(payment.getMerchantUid())
                        .member(member)
                        .orderPay(totalPrice)
                        .dayOfPayment(Timestamp.valueOf(LocalDateTime.now()))
                        .type(payment.getPayMethod())
                        .build();

                    // 주문을 DB에 넣는다.
                    Order savedOrder = this.orderRepository.save(order);

                    // order_detail 테이블에 저장할 리스트를 생성한다.
                    List<OrderDetail> createdOrderDetailList = lessonList.stream()
                        .map(lesson -> OrderDetail.builder().lesson(lesson).order(savedOrder).build())
                        .collect(Collectors.toList());

                    // order_detail 테이블에 주문한 강의를 모두 넣는다.
                    List<OrderDetail> savedOrderDetailList = this.orderDetailRepository.saveAll(createdOrderDetailList);
                } else {
                    log.error("가져온 lesson과 lessonNo 리스트의 사이즈가 다릅니다");
                }
            } else {
                message = "검증 실패";
            }
        }

        returnDto.setMessage(message);
        returnDto.setUrl(url);

        return returnDto;
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

    @Transactional
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
