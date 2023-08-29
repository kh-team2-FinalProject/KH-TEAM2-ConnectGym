package com.khteam2.connectgym.customer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CSController {

    @Autowired
    private CSService csService;

    @GetMapping(value = "/customer_service")
    public String cs_view() {
        return "content/customer_service";
    }
}
