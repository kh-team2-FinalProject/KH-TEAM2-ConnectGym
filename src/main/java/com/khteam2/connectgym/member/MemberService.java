package com.khteam2.connectgym.member;


import com.khteam2.connectgym.member.dto.MemberDTO;
import com.khteam2.connectgym.member.dto.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public long tempLoginProcess(String id, String password) {
        long returnValue = -1;
        Member member = this.memberRepository.findByUserId(id);

        log.info("tempLoginProcess Member: {}", member);

        if (member != null && member.getUserPw().equals(password)) {
            returnValue = member.getNo();
        }

        return returnValue;
    }

    public void createMember(MemberDTO memberDTO){
        Member member = new Member();

        member.setUserId(memberDTO.getUserId());
        member.setUserPw(memberDTO.getUserPw());
        member.setUserName(memberDTO.getUserName());
        member.setUserTel(memberDTO.getUserTel());
        member.setUserEmail(memberDTO.getUserEmail());
        member.setUserAddress(memberDTO.getUserAddress()); // input 추가예정

        memberRepository.save(member);

    }

    public MemberResponse findOneMember(Long no){
        Member entity = memberRepository.findById(no).orElse(null);
        return new MemberResponse(entity);
    }

}
