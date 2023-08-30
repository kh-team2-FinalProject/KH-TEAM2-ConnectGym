package com.khteam2.connectgym.customer_service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CSController {

    @Autowired
    private CSService csService;

    @GetMapping(value = "/customer_service")
    public String viewList(
        @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
        Model model) {
//      페이지당 게시글 수 세팅
        int itemsPerPage = 5; // 페이지당 아이템 수

        // 전체 카테고리의 totalPages
        List<CS> csListAll = csService.viewToAll();
        int totalPages_All = csService.getTotalPages(itemsPerPage, csListAll);
        csListAll = csService.getDataForPage(pageNumber, itemsPerPage);

        // 카테고리 1의 totalPages
        List<CS> category1 = csService.viewToCategory(1);
        int totalPages_ctgy1 = csService.getTotalPages(itemsPerPage, category1);
        category1 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category1);

        // 카테고리 2의 totalPages
        List<CS> category2 = csService.viewToCategory(2);
        int totalPages_ctgy2 = csService.getTotalPages(itemsPerPage, category2);
        category2 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category2);

        // 카테고리 리스트 (버튼용)
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csListAll", csListAll);
        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);
        model.addAttribute("ctgyList", ctgyList);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages_All", totalPages_All);
        model.addAttribute("totalPages_ctgy1", totalPages_ctgy1);
        model.addAttribute("totalPages_ctgy2", totalPages_ctgy2);

        return "content/customer_service";
    }

    @GetMapping(value = "/customer_service/categoryAll/page/{page}")
    public String viewCategoryAll(
        @RequestParam(name = "page", required = false) Integer pageNumber,
        Model model) {
        System.out.println(pageNumber);
//      페이지당 게시글 수 세팅
        int itemsPerPage = 5; // 페이지당 아이템 수
        int defaultPageNumber = 1; // 기본 페이지 번호 (첫 번째 페이지)
        
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = defaultPageNumber;
        }

        // 전체 카테고리의 totalPages
        List<CS> csListAll = csService.viewToAll();
        int totalPages_All = csService.getTotalPages(itemsPerPage, csListAll);
        csListAll = csService.getDataForPage(pageNumber, itemsPerPage);

        // 카테고리 1의 totalPages
        List<CS> category1 = csService.viewToCategory(1);
        int totalPages_ctgy1 = csService.getTotalPages(itemsPerPage, category1);
        category1 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category1);

        // 카테고리 2의 totalPages
        List<CS> category2 = csService.viewToCategory(2);
        int totalPages_ctgy2 = csService.getTotalPages(itemsPerPage, category2);
        category2 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category2);

        // 카테고리 리스트 (버튼용)
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csListAll", csListAll);
        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);
        model.addAttribute("ctgyList", ctgyList);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages_All", totalPages_All);
        model.addAttribute("totalPages_ctgy1", totalPages_ctgy1);
        model.addAttribute("totalPages_ctgy2", totalPages_ctgy2);

        return "content/customer_service";
    }

    @GetMapping(value = "/customer_service/category1/{page}")
    public String viewCategory1(
        @RequestParam(name = "page", required = false) Integer pageNumber,
        Model model) {
//      페이지당 게시글 수 세팅
        int itemsPerPage = 5; // 페이지당 아이템 수

        // 전체 카테고리의 totalPages
        List<CS> csListAll = csService.viewToAll();
        int totalPages_All = csService.getTotalPages(itemsPerPage, csListAll);
        csListAll = csService.getDataForPage(pageNumber, itemsPerPage);

        // 카테고리 1의 totalPages
        List<CS> category1 = csService.viewToCategory(1);
        int totalPages_ctgy1 = csService.getTotalPages(itemsPerPage, category1);
        category1 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category1);

        // 카테고리 2의 totalPages
        List<CS> category2 = csService.viewToCategory(2);
        int totalPages_ctgy2 = csService.getTotalPages(itemsPerPage, category2);
        category2 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category2);

        // 카테고리 리스트 (버튼용)
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csListAll", csListAll);
        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);
        model.addAttribute("ctgyList", ctgyList);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages_All", totalPages_All);
        model.addAttribute("totalPages_ctgy1", totalPages_ctgy1);
        model.addAttribute("totalPages_ctgy2", totalPages_ctgy2);

        return "content/customer_service";
    }

    @GetMapping(value = "/customer_service/category2/{page}")
    public String viewCategory2(
        @RequestParam(name = "page", required = false) Integer pageNumber,
        Model model) {
//      페이지당 게시글 수 세팅
        int itemsPerPage = 5; // 페이지당 아이템 수

        // 전체 카테고리의 totalPages
        List<CS> csListAll = csService.viewToAll();
        int totalPages_All = csService.getTotalPages(itemsPerPage, csListAll);
        csListAll = csService.getDataForPage(pageNumber, itemsPerPage);

        // 카테고리 1의 totalPages
        List<CS> category1 = csService.viewToCategory(1);
        int totalPages_ctgy1 = csService.getTotalPages(itemsPerPage, category1);
        category1 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category1);

        // 카테고리 2의 totalPages
        List<CS> category2 = csService.viewToCategory(2);
        int totalPages_ctgy2 = csService.getTotalPages(itemsPerPage, category2);
        category2 = csService.getDataForCategoryPage(pageNumber, itemsPerPage, category2);

        // 카테고리 리스트 (버튼용)
        List<CS_Category> ctgyList = csService.viewToAllCategory();

        model.addAttribute("csListAll", csListAll);
        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);
        model.addAttribute("ctgyList", ctgyList);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages_All", totalPages_All);
        model.addAttribute("totalPages_ctgy1", totalPages_ctgy1);
        model.addAttribute("totalPages_ctgy2", totalPages_ctgy2);

        return "content/customer_service";
    }

}
