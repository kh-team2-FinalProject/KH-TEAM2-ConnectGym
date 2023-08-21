package com.khteam2.connectgym.order;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.order.dto.OrderCompleteResponseDto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class OrderService {
    //    @Autowired
    //    private OrderRepository orderRepository;
    //    @Autowired
    //    private LessonRepository lessonRepository;
    //    @Autowired
    //    private MemberRepository memberRepository;
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
            .isSuccess(false)
            .build();

        // DB에서 해당 강의 리스트를 가져온다.
        List<Lesson> lessons = new ArrayList<>(); // this.lessonRepository.findAllById(lessonNolist);

        if (lessonNolist == null) {
            lessonNolist = List.of(1L, 2L);
        }

        for (Long no : lessonNolist) {
            lessons.add(new Lesson());
        }

        if (lessons.isEmpty()) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        long totalPrice = 0L;
        for (Lesson lesson : lessons) {
            // 가져온 강의의 금액을 모두 더한다.
            totalPrice += 0; // lesson.getPrice();
        }
        // DB 연계시 아래 코드 제거
        totalPrice += this.random.nextInt(99900) + 100;

        String merchantUid = this.generateOrderNo();

        // 결제 전 포트원 서버로 전송할 객체 선언. 주문 번호와 결제할 금액을 넘긴다.
        PrepareData prepareData = new PrepareData(merchantUid, BigDecimal.valueOf(totalPrice));
        // 포트원 서버로 사전 검증 요청을 전송하고 받아온다.
        IamportResponse<Prepare> postPrepareResponse = this.iamportClient.postPrepare(prepareData);

        // if (postPrepareResponse.getCode() != 0) {}

        // 회원 정보를 조회한다.
        Member member = new Member(); // this.memberRepository.findById(loginMemberNo).orElse(null);

        // View로 넘겨줄 값들을 DTO에 넘겨준다.
        String name = "홍길동";
        responseDto.setSuccess(true);
        responseDto.setLessonList(lessons);
        responseDto.setName(name); //        responseDto.setName(member.getName());
        responseDto.setTelNo("010-0000-0000"); //        responseDto.setTelNo(member.getTelNo());
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
    private String generateOrderNo() {
        String orderNo = null;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 20230820

        for (int i = 0; i < 10; i++) {
            // 1 ~ 999,999
            int randomValue = random.nextInt(999_999) + 1;
            // 20230820001024 / 20230820123456
            orderNo = String.format("%s%06d", date, randomValue);

            Order order = null; // this.orderRepository.findById(orderNo).orElse(null);
            if (order == null) {
                break;
            }
        }

        return orderNo; // this.orderRepository.getMaxNo() + 1;
    }

    /**
     * 주문 진행 중일 때 실행되는 메소드
     */
    public OrderProcessResponseDto processOrder(Long loginMemberNo, boolean isPC, String impUid, String sMerchantUid, Long sTotalPrice, String errorMsg) {
        OrderProcessResponseDto returnDto = OrderProcessResponseDto.builder()
            .success(false)
            .build();
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
            if (payment.getMerchantUid().equals(sMerchantUid)
                && payment.getAmount().equals(BigDecimal.valueOf(sTotalPrice))) {
                returnDto.setSuccess(true);
                String tempUrl = "/order/complete?orderId=" + sMerchantUid;
                url = isPC ? tempUrl : "redirect:" + tempUrl;

                // DB에 저장
//                Order order = Order.builder()
//                    .no(payment.getMerchantUid())
//                    .type(payment.getPayMethod())
//                    .build();
//
//                this.orderRepository.save(order);
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

    public OrderCompleteResponseDto completeOrder(Long loginMemberId, String orderId) {
        OrderCompleteResponseDto responseDto = OrderCompleteResponseDto.builder()
            .success(false)
            .url("/content/orderComplete")
            .build();

//        List<Order> orderList = this.orderRepository.find;

        if (orderId == null) {

        }

        return responseDto;
    }
}
