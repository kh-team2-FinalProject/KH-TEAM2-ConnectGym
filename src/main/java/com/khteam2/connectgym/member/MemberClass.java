package com.khteam2.connectgym.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum MemberClass {
    MEMBER(1, "회원"),
    TRAINER(2, "트레이너");

    private Integer code;
    private String name;
}
