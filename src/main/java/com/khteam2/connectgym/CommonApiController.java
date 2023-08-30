package com.khteam2.connectgym;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberLoginRequestDto;
import com.khteam2.connectgym.member.dto.MemberLoginResponseDto;
import com.khteam2.connectgym.trainer.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class CommonApiController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TrainerService trainerService;

    @PostMapping(value = "/api/login")
    public ResponseEntity<MemberLoginResponseDto> login(
        HttpSession session,
        @RequestBody(required = false) MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = null;


        if (requestDto.getMemberClass() == MemberClass.MEMBER) {
            responseDto = this.memberService.memberLogin(requestDto);
        } else if (requestDto.getMemberClass() == MemberClass.TRAINER) {
            responseDto = this.trainerService.trainerLogin(requestDto);
        } else {
            return ResponseEntity.badRequest().body(responseDto);
        }

        if (!responseDto.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, responseDto.getMemberNo());
        session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, responseDto.getMemberClass());

        return ResponseEntity.ok(responseDto);
    }
}
