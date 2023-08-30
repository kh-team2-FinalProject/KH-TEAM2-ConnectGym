package com.khteam2.connectgym.dietlist.dao;

import com.khteam2.connectgym.dietlist.model.FoodEntity;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodDAOImpl implements FoodDAO {
    @Autowired
    private FoodRepository foodRepository2;

    public FoodEntity saveFood(FoodEntity foodEntity){
        foodRepository2.save(foodEntity);
        return foodEntity;
    }

    public FoodEntity getFood(String foodCd){

        Optional<FoodEntity> foodEntityOptional = foodRepository2.findById(foodCd);
        return foodEntityOptional.orElse(null);

        /*FoodEntity foodEntity = foodRepository2.getById(foodCd);
        return foodEntity*/
    }



}
