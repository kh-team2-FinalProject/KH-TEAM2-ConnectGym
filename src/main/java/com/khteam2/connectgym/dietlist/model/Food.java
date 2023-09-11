package com.khteam2.connectgym.dietlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

// Data 전달 용도
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCd; // 식품 번호

    @Column(nullable = false)
    @NotBlank(message = "식품명은 필수 입력입니다.")
    private String foodNm; //식품명

    @Column(nullable = false)
    @NotNull(message = "1회 제공량은 필수 입력입니다.")
    @PositiveOrZero(message = "1회 제공량은 양수이어야 합니다.")
    private Double foodSize; // 1회 제공량

    @Column(nullable = false)
    @NotNull(message = "탄수화물은 필수 입력입니다.")
    @PositiveOrZero(message = "탄수화물은 0 또는 양수이어야 합니다.")
    private Double choc; //탄수화물

    @Column(nullable = false)
    @NotNull(message = "단백질은 필수 입력입니다.")
    @PositiveOrZero(message = "단백질은 0 또는 양수이어야 합니다.")
    private Double prot; // 단백질

    @Column(nullable = false)
    @NotNull(message = "지방은 필수 입력입니다.")
    @PositiveOrZero(message = "지방은 0 또는 양수이어야 합니다.")
    private Double fat; //지방

    private Double satFat; // 포화 지방
    private Double transFat; // 트랜스 지방

    @Column(nullable = false)
    @NotNull(message = "칼로리는 필수 입력입니다.")
    @PositiveOrZero(message = "칼로리는 0 또는 양수이어야 합니다.")
    private Double kcal; // 칼로리

    private Double nat; // 나트륨
    private Double sugar; // 당류
    private String animalPlant; // 가공업체
}
