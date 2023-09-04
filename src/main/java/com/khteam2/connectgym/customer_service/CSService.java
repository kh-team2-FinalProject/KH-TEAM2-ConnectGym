package com.khteam2.connectgym.customer_service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSService {


    @Autowired
    CS_CategoryRepository cs_categoryRepository;
    @Autowired
    private CSRepository csRepository;

    public List<CS> viewToAll() {
        List<CS> csList = csRepository.findAll();
        return csList;
    }


    public List<CS_Category> viewToAllCategory() {
        List<CS_Category> ctgyList = cs_categoryRepository.findAll();

        return ctgyList;
    }

    public List<CS> viewToCategory(int a) {

        List<CS> csList = csRepository.findAll();
        List<CS> select_categoryList = new ArrayList<>();
        for (int i = 0; i < csList.size(); i++) {
            if (csList.get(i).getCategoryId() == a) {
                select_categoryList.add(csList.get(i));
            }
        }
        return select_categoryList;
    }

    public List<CS> getDataForPage(int pageNumber, int itemsPerPage) {
        List<CS> dataForPage = new ArrayList<>();
        List<CS> allData = csRepository.findAll();

        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, allData.size());

        for (int i = startIndex; i < endIndex; i++) {
            dataForPage.add(allData.get(i));
        }
        return dataForPage;
    }

    public List<CS> getDataForCategoryPage(int pageNumber, int itemsPerPage,
        List<CS> categoryList) {
        List<CS> dataForPage = new ArrayList<>();

        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, categoryList.size());

        for (int i = startIndex; i < endIndex; i++) {
            dataForPage.add(categoryList.get(i));
        }
        return dataForPage;
    }

    public int getTotalPages(int itemsPerPage, List<CS> list) {
//        List<CS> allData = csRepository.findAll();
        int totalItems = list.size();
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

}
