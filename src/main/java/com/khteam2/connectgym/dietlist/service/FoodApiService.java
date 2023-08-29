package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.Food;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




/*
* API NEW
* http://openapi.foodsafetykorea.go.kr/api/2850a192ad63430f8bbf/I2790/xml/1/5
* */
@Service
public class FoodApiService {
    private final String apiKey = "zYPK1Bj9cpL%2FZ1nD8%2Fr56or2XJaCFvizZqM9ZQ4oxjDhjtfMHceoEtq4%2BrwcNJoynkMj5DWZk9EIxH8bwPzs8Q%3D%3D";

    private final RestTemplate restTemplate = new RestTemplate();

    public Food fetchApiData(){
        String apiUrl="http://api.data.go.kr/openapi/tn_pubr_public_nutri_info_api";
        Food response = restTemplate.getForObject(apiUrl, Food.class);
        return response;
    }

}
