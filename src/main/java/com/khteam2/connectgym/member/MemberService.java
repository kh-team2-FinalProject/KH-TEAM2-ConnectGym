package com.khteam2.connectgym.member;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.dto.MemberDto;
import com.khteam2.connectgym.member.dto.MemberLoginRequestDto;
import com.khteam2.connectgym.member.dto.MemberLoginResponseDto;
import com.khteam2.connectgym.member.dto.MemberResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    //세션 회원정보 불러오기
    public MemberResponseDto sessionMem(HttpSession session) {
        Long sessionUserNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        MemberResponseDto memberResponseDTO = findOneMember(sessionUserNo);

        return memberResponseDTO;
    }

    public void createMember(MemberDto memberDTO) {
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

    public MemberResponseDto findOneMember(Long no) {
        Member entity = memberRepository.findById(no).orElse(null);
        return new MemberResponseDto(entity);
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

    public String getAccessToken(String autorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ae7f5b9c9c8ad78f7f1cc5bbf3379d50");
            sb.append("&redirect_url=http://localhost:5000/connectgym");
            sb.append("&code=" + autorize_code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            // 출력으로 테스트
            System.out.println();
            System.out.println("response body : " + result);

            JsonObject element = JsonParser.parseString(result).getAsJsonObject();

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public HashMap<String, Object> getUserInfo(String access_Token) {
        HashMap<String, Object> userInfo = new HashMap<>();

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            System.out.println("access_Token : " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println();
            System.out.println("response body : " + result);

            JsonObject element = JsonParser.parseString(result).getAsJsonObject();

            String nickName = element.getAsJsonObject("properties").get("nickname").getAsString();
            String kakaoEmail = element.getAsJsonObject("kakao_account").get("email").getAsString();

//          추후 카카오 검수요청 완료되면 가져올 수 있는 내용들
//          String phone_number = element.getAsJsonObject("kakao_account").get("phone_number").getAsString();
//          userInfo.put("phone_number", phone_number);

            userInfo.put("nickname", nickName);
            userInfo.put("email", kakaoEmail);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public HashMap<String, Object> findMemberByEmail(String email) {
        List<Member> memberList = memberRepository.findAll();
        HashMap<String, Object> findMember = new HashMap<>();

        for (int i = 0; i < memberList.size(); i++) {
            if (email.equals(memberList.get(i).getUserEmail())) {
                findMember.put("user_no", memberList.get(i).getNo());
                findMember.put("user_id", memberList.get(i).getUserId());
                findMember.put("user_pw", memberList.get(i).getUserPw());
                findMember.put("user_name", memberList.get(i).getUserName());
                findMember.put("user_email", memberList.get(i).getUserEmail());
                if (memberList.get(i).getUserTel() != null) {
                    findMember.put("user_tel", memberList.get(i).getUserTel());
                }
                if (memberList.get(i).getUserAddress() != null) {
                    findMember.put("user_address", memberList.get(i).getUserAddress());
                }
                return findMember;
            }
        }
        return null;
    }

    public HashMap<String, Object> findMemberByNo(Long no) {
        List<Member> memberList = memberRepository.findAll();
        HashMap<String, Object> findMember = new HashMap<>();

        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getNo() == no) {
                findMember.put("user_no", memberList.get(i).getNo());
                findMember.put("user_id", memberList.get(i).getUserId());
                findMember.put("user_name", memberList.get(i).getUserName());
                findMember.put("user_email", memberList.get(i).getUserEmail());
                if (memberList.get(i).getUserTel() != null) {
                    findMember.put("user_tel", memberList.get(i).getUserTel());
                }
                if (memberList.get(i).getUserAddress() != null) {
                    findMember.put("user_address", memberList.get(i).getUserAddress());
                }
            }
        }
        return findMember;
    }

    public void updateMember(MemberDto memberDTO) {
        Member member = memberRepository.findByUserId(memberDTO.getUserId());

        member.setUserPw(memberDTO.getUserPw());
        member.setUserName(memberDTO.getUserName());
        member.setUserTel(memberDTO.getUserTel());
        if (memberDTO.getUserAddress() == null || memberDTO.getUserAddress() == "") {
            member.setUserAddress(member.getUserAddress());
        } else {
            member.setUserAddress(memberDTO.getUserAddress());
        }

        // null 일 경우 기존 데이터 그대로 저장
        memberRepository.save(member);
    }

    public String findMemberID(String name, String email) {
        Member member = memberRepository.findByUserEmail(email);

        if (member.getUserName().equals(name)) {
            return member.getUserId();
        } else {
            return null;
        }
    }

    public boolean findAndChangePW(String id, String name, String email, String password) {
        Member member = memberRepository.findByUserEmail(email);

        if (member.getUserName().equals(name) && member.getUserId().equals(id)) {
            member.setUserPw(password);
            memberRepository.save(member);
            return true;
        } else {
            return false;
        }
    }
}
