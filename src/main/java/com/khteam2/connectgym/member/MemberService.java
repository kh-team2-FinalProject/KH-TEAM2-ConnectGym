package com.khteam2.connectgym.member;


import com.khteam2.connectgym.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        member.setUserAddress("1234"); // input 추가예정

        System.out.println(member.getUserId());
        System.out.println(member.getUserPw());
        System.out.println(member.getUserName());
        System.out.println(member.getUserTel());
        System.out.println(member.getUserEmail());
        System.out.println(member.getUserAddress());
        memberRepository.save(member);

    }

}
