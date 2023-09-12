package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.dietlist.FoodTime;
import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_no", nullable = false)
    private Food food;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodTime foodTime;
    @Column(nullable = false)
    private LocalDate regDate;

    public int getFoodTimeNo() {
        return foodTime.getNo();
    }
}
