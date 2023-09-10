package com.khteam2.connectgym.fooddiary;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.dietlist.model.MemberFoodResponseDto;
import com.khteam2.connectgym.dietlist.service.MemberFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final MemberFoodService memberFoodService;

    @GetMapping("/fooddiary/calendar")
    public String getCalendar(@RequestParam(value = "year", required = false) Integer year,
                              @RequestParam(value = "month", required = false) Integer month,
                              @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
                              Model model) {
        String foodDCategory = "Calendar";
        model.addAttribute("lessonCategory", foodDCategory);
//캘린더 구현부
        LocalDate today = LocalDate.now();
        if (year == null || month == null) {
            year = today.getYear();
            month = today.getMonthValue();
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        List<List<String>> weeks = new ArrayList<>();

        int daysInMonth = yearMonth.lengthOfMonth();
        int dayCounter = 1;
        System.out.println(firstDayOfMonth.getDayOfWeek().getValue());
        while (dayCounter <= daysInMonth) {
            List<String> week = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if ((dayCounter == 1 && i < firstDayOfMonth.getDayOfWeek().getValue() % 7) || dayCounter > daysInMonth) {
                    week.add(null);
                } else {
                    week.add(String.valueOf(dayCounter));
                    dayCounter++;
                }
                System.out.println(firstDayOfMonth.getDayOfWeek().getValue() + "i : " + i);
            }
            weeks.add(week);
        }
//푸드 정보 출력용
        List<MemberFoodResponseDto> memberFoodList = memberFoodService.getUniqueDayAndFoodTime(loginMemberNo, firstDayOfMonth, firstDayOfMonth.plusMonths(1).minusDays(1));

        model.addAttribute("memberFoodList", memberFoodList);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("today", today);
        model.addAttribute("weeks", weeks);
        return "fooddiary/calendar";
    }


}
