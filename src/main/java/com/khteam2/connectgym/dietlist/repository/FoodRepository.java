package com.khteam2.connectgym.dietlist.repository;

import com.khteam2.connectgym.dietlist.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByFoodNmContains(String foodNm);

    /* foodinfo 검색 */
    Page<Food> findByFoodNmContains(String foodNm, Pageable pageable);

    Food findByFoodCd(Long FoodCd);
}
