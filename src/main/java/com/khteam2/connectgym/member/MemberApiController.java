package com.khteam2.connectgym.member;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
