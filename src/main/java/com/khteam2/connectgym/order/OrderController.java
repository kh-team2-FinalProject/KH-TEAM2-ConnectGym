package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.OrderResponseDto;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        @RequestParam(required = false) List<Long> lessonList,
        Model model
    ) throws IamportResponseException, IOException {
        // if (loginMemberNo == null) {
        //     return "redirect:/login";
        // }
        if (loginMemberNo == null) {
            loginMemberNo = 1L;
        }

        OrderResponseDto orderResponseDto = this.orderService.prepareOrder(loginMemberNo, lessonList);
        model.addAttribute("orderResponse", orderResponseDto);
        model.addAttribute("franchiseId", this.franchiseId);
        model.addAttribute("pgShopId", this.pgShopId);

        return "/content/order";
    }

    @RequestMapping(value = "/process", method = {RequestMethod.GET, RequestMethod.POST})
    public String processOrder(
        Model model,
        HttpSession session,
        @SessionAttribute(name = SessionConstant.PORTONE_ORDER_NO, required = false) String sMerchantUid,
        @SessionAttribute(name = "totalPrice", required = false) Long sTotalPrice,
        String imp_uid,
        String merchant_uid,
        String error_code,
        String error_msg
    ) {
        if (imp_uid == null || merchant_uid == null || sMerchantUid == null || sTotalPrice == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        int result = this.orderService.processOrder(imp_uid, merchant_uid);

        session.removeAttribute("merchant_uid");
        session.removeAttribute("totalPrice");

        if (result != 0) {
            model.addAttribute("message", "결제하지 못 했습니다.");
            return "/order/fail";
        }

        return "redirect:/order/complete";
    }

//    @GetMapping(value = "/complete")
//    public String completeOrder() {}

//    @ExceptionHandler({IamportResponseException.class})
//    public String portOneExceptionHandler() {}

//    @ExceptionHandler({IllegalArgumentException.class})
//    public String illegalArgumentExceptionHandler() {}

//    @ExceptionHandler({IamportResponseException.class})
//    public String IamportResponseExceptionHandler() {}
}
