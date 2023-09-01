//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodApiResponse;
import com.khteam2.connectgym.dietlist.model.OpenDataFoodNutrientDto;
import com.khteam2.connectgym.dietlist.model.OpenDataFoodNutrientFoodDto;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// 서비스 구현
@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Value("${dietApi.key}")
    private String opendataEncodedApiKey;

    @Override
    public List<Food> saveFoodsFromOpenAPI() {
        String openApiUrl = "https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?serviceKey=zYPK1Bj9cpL%2FZ1nD8%2Fr56or2XJaCFvizZqM9ZQ4oxjDhjtfMHceoEtq4%2BrwcNJoynkMj5DWZk9EIxH8bwPzs8Q%3D%3D&type=json";  // 실제 OpenAPI URL로 변경

        RestTemplate restTemplate = new RestTemplate();
        FoodApiResponse foodApiResponse = restTemplate.getForObject(openApiUrl, FoodApiResponse.class);



        Food food = convertToFoodEntity(foodApiResponse);
        foodRepository.save(food);
        return Collections.singletonList(food);
    }

    /* food api로 get */
    public OpenDataFoodNutrientDto getFoods(int pageNo, int limit) {
        OpenDataFoodNutrientDto dto = null;

        try {
            URL url = new URL(
                new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1")
                    .append("?serviceKey=")
                    .append(this.opendataEncodedApiKey)
                    .append("&type=json")
                    .append("&numOfRows=")
                    .append(limit)
                    .append("&pageNo=")
                    .append(pageNo)
                    .toString()
            );

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "*/*;q=0.9");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            String line = null;
            StringBuilder body = new StringBuilder();
            while ((line = br.readLine()) != null) {
                body.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(body.toString(), OpenDataFoodNutrientDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dto;
    }


    /* db 저장 */
    @Transactional
    public int moveDataToDatabase() {
        int page = 1;
        int pageSize = 100;
        int totalCount = 0;
        int totalPage = 1;

        while (totalPage >= page) {
            OpenDataFoodNutrientDto dto = this.getFoods(page, pageSize);
            List<Food> newFoods = dto.getBody().getItems().stream()
                .map(OpenDataFoodNutrientFoodDto::ofOpenDataFoodNutrientItemDto)
                .map(OpenDataFoodNutrientFoodDto::toEntity)
                .collect(Collectors.toList());

            this.foodRepository.saveAll(newFoods);

            totalCount = dto.getBody().getTotalCount();
            totalPage = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);

            page++;
        }

        return 1;
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



