package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.OrderProcessRequestDto;
import com.khteam2.connectgym.order.dto.OrderProcessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @Value("${portone.franchise_id}")
    private String franchiseId;
    @Value("${portone.pg_shop_id}")
    private String pgShopId;

    @PostMapping(value = "/process")
    public ResponseEntity<OrderProcessResponseDto> processOrderPc(
        HttpSession session,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long sLoginMemberNo,
        OrderProcessRequestDto requestDto) {
        if (requestDto.getImp_uid() == null
            || requestDto.getMerchant_uid() == null
            || sLoginMemberNo == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        OrderProcessResponseDto responseDto = this.orderService.processOrder(requestDto, sLoginMemberNo, true);

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
