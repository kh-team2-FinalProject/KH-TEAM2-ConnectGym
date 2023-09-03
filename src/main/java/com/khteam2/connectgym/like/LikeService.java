package com.khteam2.connectgym.like;


import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public void addLike(Long memberNo, Long lessonNo) {

        Member member = memberRepository.findById(memberNo).orElse(null);
        Lesson lesson = lessonRepository.findById(lessonNo).orElse(null);

        Like like = Like.builder()
            .member(member)
            .lesson(lesson)
            .build();

        likeRepository.save(like);
    }

    @Transactional
    public void delLike(Long memberNo, Long lessonNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        Lesson lesson = lessonRepository.findById(lessonNo).orElse(null);
        likeRepository.deleteByMemberAndLesson(member, lesson);
    }

    // 레슨 찜 수 조회
    public int likeCount(Long lessonNo) {
        return likeRepository.findAllByLessonCount(lessonNo);
    }

    // 로그인 사용자 찜 여부 체크
    public Boolean likeCheck(Long memberNo, Long lessonNo) {
        if (likeRepository.findAllByMemberNoAndLessonNo(memberNo, lessonNo) != 0) {
            return true;
        } else return false;
    }

    // 유저가 찜한 레슨 목록
    public List<LessonResponseDTO> likeingList(Long memberNo) {
        List<LessonResponseDTO> myLikeList = new ArrayList<>();

        for (Like val : likeRepository.findAllByMember(memberNo)) {
            LessonResponseDTO trainerResponseDTO = new LessonResponseDTO(val.getLesson());
            myLikeList.add(trainerResponseDTO);
        }

        return myLikeList;
    }
}
