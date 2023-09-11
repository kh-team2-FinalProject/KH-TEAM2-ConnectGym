package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @Value("${portone.franchise_id}")
    private String franchiseId;
    @Value("${portone.pg_shop_id}")
    private String pgShopId;

    @GetMapping(value = "/order/pay")
    public String order(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        HttpSession session,
        @RequestParam(required = false) List<Long> lessonList,
        Model model) {
        OrderResponseDto responseDto = this.orderService.prepareOrder(loginMemberNo, lessonList);

        if (responseDto.isSuccess()) {
            model.addAttribute("franchiseId", this.franchiseId);
            model.addAttribute("pgShopId", this.pgShopId);
        }

        model.addAttribute("orderResponse", responseDto);

        return "content/order";
    }

    @GetMapping(value = "/order/process")
    public String processOrder(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long sLoginMemberNo,
        OrderProcessRequestDto requestDto) {
        if (requestDto.getImp_uid() == null
            || requestDto.getMerchant_uid() == null
            || sLoginMemberNo == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        OrderProcessResponseDto responseDto = this.orderService.processOrder(requestDto, sLoginMemberNo, false);

        if (!responseDto.isSuccess()) {
            model.addAttribute("message", responseDto.getMessage());
        }

        return "redirect:" + responseDto.getUrl();
    }

    @GetMapping(value = "/order/complete")
    public String completeOrder(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        String orderId,
        HttpSession session) {
        OrderCompleteResponseDto responseDto = this.orderService.completeOrder(loginMemberNo, orderId);

        model.addAttribute("responseDto", responseDto);

        return responseDto.getUrl();
    }

    @GetMapping(value = "/mypage/orderList")
    public String myOrderList(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        OrderListRequestDto orderListRequestDto) {
        OrderListResponseDto responseDto = this.orderService.orderList(loginMemberNo, orderListRequestDto);

        model.addAttribute("responseDto", responseDto);
        model.addAttribute("bannerTitle", "MY ORDER LIST");

        return "mypage/orderList";
    }

    @GetMapping(value = "/order/test_complete")
    public String completeOrderTest(Model model) {
        OrderCompleteResponseDto responseDto = OrderCompleteResponseDto.builder()
            .success(true)
            .build();

        model.addAttribute("responseDto", responseDto);

        return "content/orderComplete";
    }
}
