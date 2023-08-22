package com.khteam2.connectgym.order;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.order.dto.OrderProcessDto;
import com.khteam2.connectgym.order.dto.OrderProcessRequestDto;
import com.khteam2.connectgym.order.dto.OrderProcessResponseDto;

@RestController
@RequestMapping(value = "/api/order")
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberRepository memberRepository;
    @Value("${portone.franchise_id}")
    private String franchiseId;
    @Value("${portone.pg_shop_id}")
    private String pgShopId;

    @PostMapping(value = "/process")
    public ResponseEntity<OrderProcessResponseDto> processOrderPc(
            HttpSession session,
            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long sLoginMemberNo,
            @SessionAttribute(name = SessionConstant.ORDER_ORDER_NO, required = false) String sMerchantUid,
            @SessionAttribute(name = SessionConstant.ORDER_PRICE, required = false) Long sTotalPrice,
            @SessionAttribute(name = SessionConstant.ORDER_LESSON_LIST, required = false) List<Long> sOrderLessonList,
            OrderProcessDto processDto) {
        if (processDto.getImp_uid() == null
                || processDto.getMerchant_uid() == null
                || sMerchantUid == null
                || sTotalPrice == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        OrderProcessRequestDto requestDto = OrderProcessRequestDto.builder()
                .sMerchantUid(sMerchantUid)
                .sLoginMemberNo(sLoginMemberNo)
                .sLessonNolist(sOrderLessonList)
                .sTotalPrice(sTotalPrice)
                .merchantUid(processDto.getMerchant_uid())
                .impUid(processDto.getImp_uid())
                .impSuccess(processDto.getImp_success())
                .errorCode(processDto.getError_code())
                .errorMsg(processDto.getError_msg())
                .isPC(true)
                .build();

        OrderProcessResponseDto responseDto = this.orderService.processOrder(requestDto);

        if (!responseDto.isSuccess()) {
            return ResponseEntity.badRequest().body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<Object> webhook(
            @SessionAttribute(name = SessionConstant.ORDER_ORDER_NO, required = false) String sMerchantUid,
            @SessionAttribute(name = SessionConstant.ORDER_PRICE, required = false) Long sPrice,
            String imp_uid) {
        return this.orderService.paymentCompleteWebhook(imp_uid, sMerchantUid, sPrice);
    }
}
