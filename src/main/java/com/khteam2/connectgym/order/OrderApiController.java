package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.OrderProcessResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/order")
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    @Value("${portone.franchise_id}")
    private Object franchiseId;
    @Value("${portone.pg_shop_id}")
    private Object pgShopId;

    @GetMapping(value = "/temp_login")
    public ResponseEntity<Object> tempLogin(HttpSession session) {
        session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, 1);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/temp_logout")
    public ResponseEntity<Object> tempLogout(HttpSession session) {
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_NO);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/process")
    public ResponseEntity<OrderProcessResponseDto> processOrderPc(
        HttpSession session,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @SessionAttribute(name = SessionConstant.PORTONE_ORDER_NO, required = false) String sMerchantUid,
        @SessionAttribute(name = SessionConstant.PORTONE_PRICE, required = false) Long sTotalPrice,
        String imp_uid,
        String merchant_uid,
        @RequestParam(required = false) String error_code,
        @RequestParam(required = false) String error_msg
    ) {
        if (imp_uid == null || merchant_uid == null || sMerchantUid == null || sTotalPrice == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        OrderProcessResponseDto responseDto = this.orderService.processOrder(loginMemberNo, true, imp_uid, sMerchantUid, sTotalPrice, error_msg);

        if (!responseDto.isSuccess()) {
            return ResponseEntity.badRequest().body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<Object> webhook(
        @SessionAttribute(name = SessionConstant.PORTONE_ORDER_NO, required = false) String sMerchantUid,
        @SessionAttribute(name = SessionConstant.PORTONE_PRICE, required = false) Long sPrice,
        String imp_uid
    ) {
        return this.orderService.paymentCompleteWebhook(imp_uid, sMerchantUid, sPrice);
    }
}
