package com.khteam2.connectgym.dietlist.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;





@Service
public class FoodApiService {
    private String apiKey;

    public FoodApiService(@Value("${dietApi.key}") String apiKey) {
        this.apiKey = apiKey;
    }
    public String getFoodInfo(String foodCode) {
        // API 호출에 필요한 URL 생성
        String apiUrl = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1?serviceKey=" + apiKey
            + "&type=xml";

        RestTemplate restTemplate = new RestTemplate();

        // API 호출 및 결과 받아오기
        String result = restTemplate.getForObject(apiUrl, String.class);

        return result;
    }
}
