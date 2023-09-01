package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.follow.dto.FollowTrainerResponseDTO;
import com.khteam2.connectgym.follow.dto.FollowUserResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Transactional
    public void addFollow(Long fromUserNo,Long toTrainerNo) {

        Member fromMember = memberRepository.findById(fromUserNo).orElse(null);
        Trainer toTrainer = trainerRepository.findById(toTrainerNo).orElse(null);

        Follow follow = Follow.builder()
            .fromUser(fromMember)
            .toTrainer(toTrainer)
            .build();

        followRepository.save(follow);
    }

    @Transactional
    public void delFollow(Long fromUserNo, Long toTrainerNo) {
        Member fromMember = memberRepository.findById(fromUserNo).orElse(null);
        Trainer toTrainer = trainerRepository.findById(toTrainerNo).orElse(null);
        followRepository.deleteByFromUserAndToTrainer(fromMember, toTrainer);
    }

    // 트레이너를 팔로우한 회원 목록 > 트레이너에게만 공개
    public FollowTrainerResponseDTO followStatus(Long trainerNo){
        List<Member> followList = new ArrayList<>();

        for(Follow val : followRepository.findAllByToTrainer(trainerNo)){
            followList.add(val.getFromUser());
        }

        FollowTrainerResponseDTO followTrainerResponseDTO = FollowTrainerResponseDTO.builder()
            .followUserList(followList)
            .trainerFollowCnt(followList.size())
            .build();

        return followTrainerResponseDTO;
    }

    // 트레이너 팔로우 수 조회
    public int followCount(Long trainerNo){
        return followRepository.findAllByToTrainerCount(trainerNo);
    }

    //팔로우 여부 체크
    public Boolean followCheck(Long userNo, Long trainerNo) {
        if(followRepository.findAllByFromUserNoAndTrainerNo(userNo,trainerNo) != 0){
            return true;
        } else return false;
    }

    // 유저가 팔로우한 트레이너 목록


}
