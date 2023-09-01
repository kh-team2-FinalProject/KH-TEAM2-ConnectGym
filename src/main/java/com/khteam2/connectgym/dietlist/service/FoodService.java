//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.OpenDataFoodNutrientDto;

import java.util.List;

// OpenAPI에서 음식 데이터를 가져와 저장하는 메소드
public interface FoodService {
    List<Food> saveFoodsFromOpenAPI();
    // 필요한 추가 메서드를 선언

    OpenDataFoodNutrientDto getFoods(int pageNo, int limit);

    int moveDataToDatabase();
}
