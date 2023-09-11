package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.dietlist.FoodTime;
import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFoodResponseDto {
    private Long no;
    private Member member;
    private Food food;
    private FoodTime foodTime;
    private LocalDate regDate;
    private String day;

    public MemberFoodResponseDto formEntity(MemberFood memberFood) {
        return MemberFoodResponseDto.builder()
            .no(memberFood.getNo())
            .member(memberFood.getMember())
            .food(memberFood.getFood())
            .foodTime(memberFood.getFoodTime())
            .regDate(memberFood.getRegDate())
            .day(String.valueOf(memberFood.getRegDate().getDayOfMonth()))
            .build();
    }
}
