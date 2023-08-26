//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.model;

import javax.persistence.*;

@Entity
@Table(
    name = "foods"
)
public class Food {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private double fat;
    private double carbohydrate;
    private double protein;
    private double calories;

    public Food() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFat() {
        return this.fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return this.carbohydrate;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrate = carbohydrates;
    }

    public double getProtein() {
        return this.protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCalories() {
        return this.calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
