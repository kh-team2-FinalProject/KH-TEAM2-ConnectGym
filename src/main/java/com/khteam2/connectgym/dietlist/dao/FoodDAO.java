package com.khteam2.connectgym.dietlist.dao;

import com.khteam2.connectgym.dietlist.model.FoodEntity;

public interface FoodDAO {

    // food 조회 저장 받아올 메서드
    FoodEntity saveFood(FoodEntity foodEntity);

    FoodEntity getFood(String foodCd);
}
