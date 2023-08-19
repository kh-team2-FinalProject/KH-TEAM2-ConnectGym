package com.khteam2.connectgym.member;

import org.springframework.web.bind.annotation.GetMapping;

public class MemberController {


    @GetMapping("/mypage/mylessons")
    public String mylessons(){
        return "mylessons";
    }

}
