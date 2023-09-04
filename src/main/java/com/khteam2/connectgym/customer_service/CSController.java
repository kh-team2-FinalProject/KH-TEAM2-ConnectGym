package com.khteam2.connectgym.customer_service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CSController {

    @Autowired
    private final CSService csService;

    @GetMapping(value = "/customer/faq")
    public String viewFaq(
        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
        @RequestParam(name = "category", required = false, defaultValue = "All") String category,
        Model model) {
//      페이지당 게시글 수 세팅
        int itemsPerPage = 5; // 페이지당 아이템 수

        List<CS> csList = new ArrayList<>();
        int totalPages = 0;
        if (category.equals("All")) {
            csList = csService.viewToAll();
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForPage(pageNumber, itemsPerPage);

        } else if (category.equals("1")) {
            csList = csService.viewToCategory(1);
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForCategoryPage(pageNumber, itemsPerPage, csList);
        } else if (category.equals("2")) {
            csList = csService.viewToCategory(2);
            totalPages = csService.getTotalPages(itemsPerPage, csList);
            csList = csService.getDataForCategoryPage(pageNumber, itemsPerPage, csList);
        }

        // 카테고리 리스트 (버튼용)
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csList", csList);
        model.addAttribute("ctgyList", ctgyList);

        model.addAttribute("category", category);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);

        return "content/faq";
    }

//    @GetMapping(value = "/customer/notice")
//    public String viewNotice(
//        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
//        @RequestParam(name = "category", required = false, defaultValue = "All") String category,
//        Model model) {
////      페이지당 게시글 수 세팅
//        int itemsPerPage = 5; // 페이지당 아이템 수
//
//        List<CS> csList = new ArrayList<>();
//        int totalPages = 0;
//        if (category.equals("All")) {
//            csList = csService.viewToAll();
//            totalPages = csService.getTotalPages(itemsPerPage, csList);
//            csList = csService.getDataForPage(pageNumber, itemsPerPage);
//
//        } else if (category.equals("1")) {
//            csList = csService.viewToCategory(1);
//            totalPages = csService.getTotalPages(itemsPerPage, csList);
//            csList = csService.getDataForCategoryPage(pageNumber, itemsPerPage, csList);
//        } else if (category.equals("2")) {
//            csList = csService.viewToCategory(2);
//            totalPages = csService.getTotalPages(itemsPerPage, csList);
//            csList = csService.getDataForCategoryPage(pageNumber, itemsPerPage, csList);
//        }
//
//        // 카테고리 리스트 (버튼용)
//        List<CS_Category> ctgyList = csService.viewToAllCategory();
//
//        model.addAttribute("csList", csList);
//        model.addAttribute("ctgyList", ctgyList);
//
//        model.addAttribute("category", category);
//        model.addAttribute("currentPage", pageNumber);
//        model.addAttribute("totalPages", totalPages);
//
//        return "content/notice";
//    }
}
