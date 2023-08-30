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
            if (csList.get(i).getCategory_id() == a) {
                select_categoryList.add(csList.get(i));
            }
        }
        return select_categoryList;
    }

}
