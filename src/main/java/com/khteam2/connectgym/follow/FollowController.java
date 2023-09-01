package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.common.SessionConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{toTrainerNo}")
    public ResponseEntity<String> follow(@PathVariable Long toTrainerNo, HttpSession session) {
        Long fromUserNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        followService.addFollow(fromUserNo, toTrainerNo);
        return ResponseEntity.ok().body("Follow");
    }

    @DeleteMapping("/follow/{toTrainerNo}")
    public ResponseEntity<String> unFollow(@PathVariable Long toTrainerNo,HttpSession session){
        Long fromUserNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        followService.delFollow(fromUserNo, toTrainerNo);
        return ResponseEntity.ok().body("unFollow");
    }
}
