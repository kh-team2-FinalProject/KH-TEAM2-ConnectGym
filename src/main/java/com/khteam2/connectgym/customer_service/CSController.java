package com.khteam2.connectgym.customer_service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CSController {

    @Autowired
    private CSService csService;

    @GetMapping(value = "/customer_service")
    public String viewList(Model model) {

//      전체 조회
        List<CS> csList = csService.viewToAll();
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csList", csList);
        model.addAttribute("ctgyList", ctgyList);

        List<CS> category1 = csService.viewToCategory(1);

        return "content/customer_service";
    }
}
