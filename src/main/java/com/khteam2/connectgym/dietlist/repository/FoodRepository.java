package com.khteam2.connectgym.dietlist.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.khteam2.connectgym.dietlist.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByAnimalPlantContains(String animalPlant, Pageable pageable);


    List<Food> findByFoodNmContains(String foodNm, Pageable pageable);


}
