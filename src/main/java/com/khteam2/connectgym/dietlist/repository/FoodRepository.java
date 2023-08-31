package com.khteam2.connectgym.dietlist.repository;

import com.khteam2.connectgym.dietlist.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
//    List<Food> findByAnimal_PlantContains(String animal_plant);
}
// JpaRepository를 통해 기본적인 CRUD(Create, Read, Update, Delete) 메소드가 제공됨
