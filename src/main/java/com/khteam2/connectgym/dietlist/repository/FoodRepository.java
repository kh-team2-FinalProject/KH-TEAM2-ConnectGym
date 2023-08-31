
package com.khteam2.connectgym.dietlist.repository;

import com.khteam2.connectgym.dietlist.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
// JpaRepository를 통해 기본적인 CRUD(Create, Read, Update, Delete) 메소드가 제공됨
