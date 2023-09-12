package com.khteam2.connectgym.like;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

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
    public List<LessonResponseDTO> likingList(Long memberNo) {
        List<LessonResponseDTO> myLikeList = new ArrayList<>();

        for (Like val : likeRepository.findAllByMember(memberNo)) {
            LessonResponseDTO trainerResponseDTO = new LessonResponseDTO(val.getLesson());
            myLikeList.add(trainerResponseDTO);
        }

        return myLikeList;
    }

    //레슨넘버로 찜한 사람 목록 가져오기
    public List<MemberResponseDTO> likedList(Long lessonNo) {
        List<MemberResponseDTO> likedMembers = new ArrayList<>();
        try {
            for (Like like : likeRepository.findAllByLessonNo(lessonNo)) {
                MemberResponseDTO memberResponseDTO = new MemberResponseDTO(like.getMember());
                likedMembers.add(memberResponseDTO);
            }
        } catch (Exception e) {
            logger.error("likedList에러" + e.getMessage());
            e.printStackTrace();
        }
        return likedMembers;
    }

    //트레이너명 또는 강사명으로 검색
    public List<LessonResponseDTO> searchLike(Long userNo, String keyword) {
        List<LessonResponseDTO> likingList = new ArrayList<>();

        if (keyword == "") {
            return likingList(userNo);
        }

        for (Like val : likeRepository.searchByTrainerNameOrLessonTitle(userNo, keyword)) {
            LessonResponseDTO lessonResponseDTO = new LessonResponseDTO(val.getLesson());
            likingList.add(lessonResponseDTO);
        }

        return likingList;
    }
}
