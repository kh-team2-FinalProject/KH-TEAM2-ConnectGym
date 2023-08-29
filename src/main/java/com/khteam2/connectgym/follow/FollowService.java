package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;

    public void addFollow(Member fromUser, Trainer toTrainer) {
        Follow follow = new Follow();
        follow.setFromUser(fromUser);
        follow.setToTrainer(toTrainer);
        followRepository.save(follow);
    }


}
