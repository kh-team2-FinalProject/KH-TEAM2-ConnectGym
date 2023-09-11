package com.khteam2.connectgym.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/api/check_overlap_id")
    public ResponseEntity<Object> findUserID(@RequestParam("user_id") String user_id) {
        System.out.println("user_id: " + user_id);
        Map<String, Object> map = new HashMap<>();

        boolean result = memberService.overlap_userID(user_id);

        map.put("id", user_id);
        map.put("boolean", result);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/mypage/update/myInfo")
    public ResponseEntity<Object> placeholderInupdatePage(@RequestParam("user_no") Long user_no) {
        Map<String, Object> map = new HashMap<>();

        map = memberService.findMemberByNo(user_no);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/findIdProcess")
    public ResponseEntity<Object> findIdProcess(
        @RequestParam("name") String name,
        @RequestParam("email") String email
    ) {
        String findID = memberService.findMemberID(name, email);

        if (findID != null) {
            return ResponseEntity.ok(findID);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/findPWProcess")
    public ResponseEntity<Object> findPwProcess(
        @RequestParam("userId") String userId,
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password
    ) {
        boolean result = memberService.findAndChangePW(userId, name, email, password);

        if (result) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
