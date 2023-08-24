package com.khteam2.connectgym.fooddiary;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {

    private Map<String, List<Todo>> todosMap = new HashMap<>();

    @GetMapping("/fooddiary/calendar")
    public String getCalendar(Model model) {
        LocalDate today = LocalDate.now();

        List<List<String>> weeks = new ArrayList<>();

        int daysInMonth = today.lengthOfMonth();
        int dayCounter = 1;

        while (dayCounter <= daysInMonth) {
            List<String> week = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if ((dayCounter == 1 && i < today.getDayOfWeek().getValue()) || dayCounter > daysInMonth) {
                    week.add(null);
                } else {
                    week.add(String.valueOf(dayCounter));
                    dayCounter++;
                }
            }
            weeks.add(week);
        }
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
