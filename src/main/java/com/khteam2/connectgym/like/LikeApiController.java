package com.khteam2.connectgym.like;


import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.dto.FollowForUserResponseDTO;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.like.dto.LikeDto;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @PostMapping("/like/search")
    public List<LessonResponseDTO> searchFollow(HttpSession session, @RequestBody(required = false) LikeDto like){
        Long userNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);

        return likeService.searchLike(userNo, like.getKeyword());
    }


}


