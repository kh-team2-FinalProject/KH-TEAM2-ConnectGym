package com.khteam2.connectgym.customer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CSApiController {
    @Autowired
    private CSService csService;

    @GetMapping("/api/customer/faq")
    public ResponseEntity<Map<String, Object>> liveView(
        @RequestParam("page") Integer page,
        @RequestParam("category") String category
    ) {
        int itemsPerPage = 5;

        List<CS> csList = new ArrayList<>();
        int totalPages = 0;
        if (category.equals("All")) {
            csList = csService.viewToAll();
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForPage(page, itemsPerPage);

        } else if (category.equals("1")) {
            csList = csService.viewToCategory(1);
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForCategoryPage(page, itemsPerPage, csList);
        } else if (category.equals("2")) {
            csList = csService.viewToCategory(2);
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForCategoryPage(page, itemsPerPage, csList);
        }

        List<CS_Category> ctgyList = csService.viewToAllCategory();

        Map<String, Object> response = new HashMap<>();

        response.put("csList", csList);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        response.put("ctgyList", ctgyList);
        response.put("category", category);

        return ResponseEntity.ok(response);
    }
}
