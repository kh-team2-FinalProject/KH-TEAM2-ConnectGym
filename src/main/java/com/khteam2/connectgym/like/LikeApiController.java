package com.khteam2.connectgym.like;


import com.khteam2.connectgym.common.SessionConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LikeApiController {
    private final LikeService likeService;

    @PostMapping("/like/{lessonNo}")
    public ResponseEntity<String> like(@PathVariable Long lessonNo, HttpSession session) {
        Long memberNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        likeService.addLike(memberNo, lessonNo);
        return ResponseEntity.ok().body("Like");
    }

    @DeleteMapping("/like/{lessonNo}")
    public ResponseEntity<String> unlike(@PathVariable Long lessonNo,HttpSession session){
        Long memberNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        likeService.delLike(memberNo, lessonNo);
        return ResponseEntity.ok().body("unLike");
    }


}


