package com.khteam2.connectgym.member;

import com.khteam2.connectgym.member.dto.MemberDTO;
import com.khteam2.connectgym.member.dto.MemberLoginRequestDto;
import com.khteam2.connectgym.member.dto.MemberLoginResponseDto;
import com.khteam2.connectgym.member.dto.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createMember(MemberDTO memberDTO) {
        Member member = new Member();

        member.setUserId(memberDTO.getUserId());
        member.setUserPw(memberDTO.getUserPw());
        member.setUserName(memberDTO.getUserName());
        member.setUserTel(memberDTO.getUserTel());
        member.setUserEmail(memberDTO.getUserEmail());
        if (memberDTO.getUserAddress() != null) {
            member.setUserAddress(memberDTO.getUserAddress());
        }
        memberRepository.save(member);

    }

    public MemberResponse findOneMember(Long no) {
        Member entity = memberRepository.findById(no).orElse(null);
        return new MemberResponse(entity);
    }

    public boolean overlap_userID(String user_id) {
        List<Member> memberList = memberRepository.findAll();
        boolean res = false;

        for (int i = 0; i < memberList.size(); i++) {
            if (user_id.equals(memberList.get(i).getUserId())) {
                res = true;
            }
        }
        return res;
    }

    public MemberLoginResponseDto memberLogin(MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = MemberLoginResponseDto.builder()
            .success(false)
            .build();

        if (requestDto == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        String id = requestDto.getId();
        String password = requestDto.getPassword();

        if (id == null || id.isBlank() || password == null || password.isBlank()) {
            responseDto.setMessage("ID 또는 비밀번호가 비어있습니다.");
            return responseDto;
        }

        Member member = this.memberRepository.findByUserId(id);

        if (member == null || !member.getUserPw().equals(password)) {
            responseDto.setMessage("ID 또는 비밀번호를 확인해 주시기 바랍니다.");
            return responseDto;
        }

        responseDto.setSuccess(true);
        responseDto.setMemberNo(member.getNo());
        responseDto.setMemberClass(MemberClass.MEMBER);

        return responseDto;
    }
}
