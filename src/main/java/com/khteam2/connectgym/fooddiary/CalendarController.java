package com.khteam2.connectgym.fooddiary;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {

    private Map<String, List<Todo>> todosMap = new HashMap<>();

    @GetMapping("/fooddiary/calendar")
        public String getCalendar(@RequestParam(value = "year", required = false) Integer year,
                                  @RequestParam(value = "month", required = false) Integer month,
                                  Model model) {
        String foodDCategory = "Calendar";
        model.addAttribute("lessonCategory",foodDCategory);

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
                if ((dayCounter == 1 && i < firstDayOfMonth.getDayOfWeek().getValue() %7 ) || dayCounter > daysInMonth) {
                    week.add(null);
                } else {
                    week.add(String.valueOf(dayCounter));
                    dayCounter++;
                }System.out.println(firstDayOfMonth.getDayOfWeek().getValue()  +"i : "+ i);
            }
            weeks.add(week);
        }


        model.addAttribute("yearMonth",yearMonth);
        model.addAttribute("today",today);
        model.addAttribute("weeks", weeks);
        model.addAttribute("todosMap", todosMap);
        return "/fooddiary/calendar";
    }

    @PostMapping("/addTodo")
    public String addTodo(@RequestParam String date, @RequestParam String task) {
        Todo todo = new Todo(date, task);
        todosMap.computeIfAbsent(date, k -> new ArrayList<>()).add(todo);
        return "redirect:/";
    }
}
