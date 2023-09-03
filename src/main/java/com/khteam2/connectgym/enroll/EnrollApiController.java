package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.enroll.dto.EnrollResponseDto;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollApiController {

    private final MemberService memberService;
    private final EnrollService enrollService;



    @GetMapping("/api/enrollList")
    public List<EnrollResponseDto> enrollList(HttpSession session) {

        MemberResponseDTO member = memberService.sessionMem(session);

        return enrollService.enrollList(member);
    }

}
