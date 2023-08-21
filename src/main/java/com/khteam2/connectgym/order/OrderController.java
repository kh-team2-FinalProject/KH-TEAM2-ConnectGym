package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.OrderCompleteResponseDto;
import com.khteam2.connectgym.order.dto.OrderProcessResponseDto;
import com.khteam2.connectgym.order.dto.OrderResponseDto;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Value("${portone.franchise_id}")
    private Object franchiseId;
    @Value("${portone.pg_shop_id}")
    private Object pgShopId;


    @GetMapping(value = "/pay")
    public String order(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        HttpSession session,
        @RequestParam(required = false) List<Long> lessonList,
        Model model
    ) throws IamportResponseException, IOException {
        // if (loginMemberNo == null) {
        //     return "redirect:/login";
        // }
        if (loginMemberNo == null) {
            loginMemberNo = 1L;
        }

        OrderResponseDto responseDto = this.orderService.prepareOrder(loginMemberNo, lessonList);

        session.setAttribute(SessionConstant.PORTONE_ORDER_NO, responseDto.getOrderNo());
        session.setAttribute(SessionConstant.PORTONE_PRICE, responseDto.getPrice());

        model.addAttribute("orderResponse", responseDto);
        model.addAttribute("franchiseId", this.franchiseId);
        model.addAttribute("pgShopId", this.pgShopId);

        return "/content/order";
    }

    @GetMapping(value = "/process")
    public String processOrder(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @SessionAttribute(name = SessionConstant.PORTONE_ORDER_NO, required = false) String sMerchantUid,
        @SessionAttribute(name = SessionConstant.PORTONE_PRICE, required = false) Long sTotalPrice,
        String imp_uid,
        String merchant_uid,
        String error_code,
        String error_msg
    ) {
        // if (loginMemberNo == null) {
        //     return "redirect:/login";
        // }

        if (imp_uid == null || merchant_uid == null || sMerchantUid == null || sTotalPrice == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        OrderProcessResponseDto responseDto = this.orderService.processOrder(loginMemberNo, false, imp_uid, sMerchantUid, sTotalPrice, error_msg);

        if (!responseDto.isSuccess()) {
            model.addAttribute("message", responseDto.getMessage());
        }

        return responseDto.getUrl();
    }

    @GetMapping(value = "/complete")
    public String completeOrder(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        String orderId,
        HttpSession session) {
        // if (loginMemberNo == null) {
        //     return "redirect:/login";
        // }

        OrderCompleteResponseDto responseDto = this.orderService.completeOrder(loginMemberNo, orderId);

        session.removeAttribute(SessionConstant.PORTONE_ORDER_NO);
        session.removeAttribute(SessionConstant.PORTONE_PRICE);

        return responseDto.getUrl();
    }

//    @ExceptionHandler({IamportResponseException.class})
//    public String portOneExceptionHandler() {}

//    @ExceptionHandler({IllegalArgumentException.class})
//    public String illegalArgumentExceptionHandler() {}

//    @ExceptionHandler({IamportResponseException.class})
//    public String IamportResponseExceptionHandler() {}
}
