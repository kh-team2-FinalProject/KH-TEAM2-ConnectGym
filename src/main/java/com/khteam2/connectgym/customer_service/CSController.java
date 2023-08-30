package com.khteam2.connectgym.customer_service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CSController {

    @Autowired
    private CSService csService;

    @GetMapping(value = "/customer_service")
    public String viewList(
        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
        Model model) {
//      페이징
        int itemsPerPage = 5; // 페이지당 아이템 수

        int totalPages = csService.getTotalPages(itemsPerPage);
        List<CS> dataForPage = csService.getDataForPage(pageNumber, itemsPerPage);
//      전체 조회
//        List<CS> csList = csService.viewToAll();
        List<CS_Category> ctgyList = csService.viewToAllCategory();

//        model.addAttribute("csList", csList);
        model.addAttribute("csList", dataForPage);
        model.addAttribute("ctgyList", ctgyList);

//      카테고리 id별로 가져오기
        List<CS> category1 = csService.viewToCategory(1);
        List<CS> category2 = csService.viewToCategory(2);

        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);

        return "content/customer_service";
    }

    @GetMapping("/cs/page/{pageNumber}")
    public String getPage(@PathVariable(name = "pageNumber", required = false) Integer pageNumber,
        Model model) {
        int defaultPageNumber = 1; // 기본 페이지 번호 (첫 번째 페이지)
        int itemsPerPage = 5; // 페이지당 아이템 수

        if (pageNumber == null || pageNumber < 1) {
            pageNumber = defaultPageNumber;
        }

        List<CS> dataForPage = csService.getDataForPage(pageNumber, itemsPerPage);
        int totalPages = csService.getTotalPages(itemsPerPage);

        model.addAttribute("csList", dataForPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);

        return "content/customer_service";
    }

}
