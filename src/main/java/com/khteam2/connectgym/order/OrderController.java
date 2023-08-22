package com.khteam2.connectgym.order;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.order.dto.*;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Value("${portone.franchise_id}")
    private String franchiseId;
    @Value("${portone.pg_shop_id}")
    private String pgShopId;


    @GetMapping(value = "/pay")
    public String order(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        HttpSession session,
        @RequestParam(required = false) List<Long> lessonList,
        Model model
    ) throws IamportResponseException, IOException {
        if (loginMemberNo == null) {
            return "redirect:/temp_login";
        }

        OrderResponseDto responseDto = this.orderService.prepareOrder(loginMemberNo, lessonList);

        session.setAttribute(SessionConstant.ORDER_ORDER_NO, responseDto.getOrderNo());
        session.setAttribute(SessionConstant.ORDER_PRICE, responseDto.getPrice());
        session.setAttribute(SessionConstant.ORDER_LESSON_LIST, lessonList);

        model.addAttribute("orderResponse", responseDto);
        model.addAttribute("franchiseId", this.franchiseId);
        model.addAttribute("pgShopId", this.pgShopId);

        return "/content/order";
    }

    @GetMapping(value = "/process")
    public String processOrder(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long sLoginMemberNo,
        @SessionAttribute(name = SessionConstant.ORDER_ORDER_NO, required = false) String sMerchantUid,
        @SessionAttribute(name = SessionConstant.ORDER_PRICE, required = false) Long sTotalPrice,
        @SessionAttribute(name = SessionConstant.ORDER_LESSON_LIST, required = false) List<Long> sOrderLessonList,
        OrderProcessDto processDto
    ) {
        if (sLoginMemberNo == null) {
            return "redirect:/temp_login";
        }

        if (processDto.getImp_uid() == null
            || processDto.getMerchant_uid() == null
            || sMerchantUid == null
            || sTotalPrice == null
        ) {
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
            .isPC(false)
            .build();

        OrderProcessResponseDto responseDto = this.orderService.processOrder(requestDto);

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
        HttpSession session
    ) {
        if (loginMemberNo == null) {
            return "redirect:/temp_login";
        }

        OrderCompleteResponseDto responseDto = this.orderService.completeOrder(loginMemberNo, orderId);

        if (responseDto.isSuccess()) {
            session.removeAttribute(SessionConstant.ORDER_ORDER_NO);
            session.removeAttribute(SessionConstant.ORDER_LESSON_LIST);
            session.removeAttribute(SessionConstant.ORDER_PRICE);
        }

        return responseDto.getUrl();
    }

//    @ExceptionHandler({IamportResponseException.class})
//    public String portOneExceptionHandler() {}

//    @ExceptionHandler({IllegalArgumentException.class})
//    public String illegalArgumentExceptionHandler() {}

//    @ExceptionHandler({IamportResponseException.class})
//    public String IamportResponseExceptionHandler() {}
}
