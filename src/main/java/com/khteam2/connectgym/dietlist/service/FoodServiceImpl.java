//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodApiResponse;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 서비스 구현
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {

        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> saveFoodsFromOpenAPI() {
        String openApiUrl = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1?serviceKey=zYPK1Bj9cpL%2FZ1nD8%2Fr56or2XJaCFvizZqM9ZQ4oxjDhjtfMHceoEtq4%2BrwcNJoynkMj5DWZk9EIxH8bwPzs8Q%3D%3D&type=xml";  // 실제 OpenAPI URL로 변경

        RestTemplate restTemplate = new RestTemplate();
        FoodApiResponse[] foodApiResponses = restTemplate.getForObject(openApiUrl, FoodApiResponse[].class);

        // ModelMapper를 사용하여 API 응답과 엔티티 간의 필드 매핑 자동화
        List<Food> foods = Arrays.stream(foodApiResponses)
            .map(this::convertToFoodEntity)
            .collect(Collectors.toList());

        return foodRepository.saveAll(foods);
    }

    private Food convertToFoodEntity(FoodApiResponse apiResponse) {
        Food food = new Food();
        food.setFoodNm(apiResponse.getFoodNm());
        food.setFoodSize(apiResponse.getFoodSize());
        food.setChoc(apiResponse.getChoc());
        food.setProt(apiResponse.getProt());
        food.setFat(apiResponse.getFat());
        food.setSat_fat(apiResponse.getSat_fat());
        food.setTrans_fat(apiResponse.getTrans_fat());
        food.setKcal(apiResponse.getKcal());
        food.setNat(apiResponse.getNat());
        food.setSugar(apiResponse.getSugar());
        food.setAnimal_plant(apiResponse.getAnimal_plant());
        return food;
    }


}



