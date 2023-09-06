package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.member.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Builder
@Getter
public class MemberFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "food_no")
    private Food food;
    @Enumerated(EnumType.STRING)
    private FoodTime foodTime;
}
