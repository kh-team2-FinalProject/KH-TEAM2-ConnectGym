//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khteam2.connectgym.dietlist.FoodTime;
import com.khteam2.connectgym.dietlist.model.*;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.repository.MemberFoodRepository;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberRepository memberRepository;

    @Value("${dietApi.key}")
    private String opendataEncodedApiKey;


    /* food api로 get */
    public FoodNutrientDto getFoods(int pageNo, int limit) {
        FoodNutrientDto dto = null;

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
            dto = mapper.readValue(body.toString(), FoodNutrientDto.class);
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
            FoodNutrientDto dto = this.getFoods(page, pageSize);
            List<Food> newFoods = dto.getBody().getItems().stream()
                .map(FoodNutrientFoodDto::ofOpenDataFoodNutrientItemDto)
                .map(FoodNutrientFoodDto::toEntity)
                .collect(Collectors.toList());

            this.foodRepository.saveAll(newFoods);

            totalCount = dto.getBody().getTotalCount();
            totalPage = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);
            page++;
        }

        return 1;
    }

    @Transactional
    @Override
    public Food saveFood(Food food) {

        return foodRepository.save(food);
    }

    /* 유효성 체크해서 에러 메세지 */
    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }


    /**/
    @Transactional(readOnly = true)
    @Override
    public List<Food> searchFood(String key) {
        List<Food> foodinfo = new ArrayList<>();

        List<Food> foods = foodRepository.findByFoodNmContains(key);
        for (Food food : foods) {
            foodinfo.add(food);
        }

        return foodinfo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Food> searchDiet(String key) {
        List<Food> dietList = new ArrayList<>();

        List<Food> foods = foodRepository.findByFoodNmContains(key);
        for (Food food : foods) {
            dietList.add(food);
        }

        return dietList;
    }


    @Transactional
    @Override
    public Food selectFood(Long selectedKey) {
        Food selfood = foodRepository.findByFoodCd(selectedKey);
        return selfood;
    }

    @Transactional
    @Override
    public FoodInsertResponseDto insertFood(FoodInsertRequestDto requestDto, Long loginMemberNo) {
        FoodInsertResponseDto responseDto = FoodInsertResponseDto.builder()
            .success(false)
            .build();

        Long foodNo = requestDto.getSelectedKey();
        FoodTime foodTime = requestDto.getFoodTime();
        LocalDate date = requestDto.getDate();

        if (foodNo == null || foodTime == null || date == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        if (loginMemberNo == null) {
            responseDto.setMessage("로그인되어 있지 않습니다.");
            return responseDto;
        }

        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        if (member == null) {
            responseDto.setMessage("사용자 정보가 없습니다.");
            return responseDto;
        }

        Food food = this.foodRepository.findById(foodNo).orElse(null);

        if (food == null) {
            responseDto.setMessage("해당 음식이 없습니다.");
            return responseDto;
        }

        MemberFood memberFood = MemberFood.builder()
            .food(food)
            .member(member)
            .foodTime(foodTime)
            .build();

        this.memberFoodRepository.save(memberFood);

        responseDto.setSuccess(true);

        return responseDto;
    }
}



