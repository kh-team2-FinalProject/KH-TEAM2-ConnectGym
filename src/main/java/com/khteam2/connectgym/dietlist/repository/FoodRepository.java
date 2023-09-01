package com.khteam2.connectgym.dietlist.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.khteam2.connectgym.dietlist.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByAnimalPlantContains(String animalPlant, Pageable pageable);
}
// JpaRepository를 통해 기본적인 CRUD(Create, Read, Update, Delete) 메소드가 제공됨
