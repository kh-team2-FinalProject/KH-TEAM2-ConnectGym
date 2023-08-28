package com.khteam2.connectgym.trainer;


import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long registerTrainer(TrainerRequestDTO trainerRequestDTO, Member member,
                                  MultipartFile profileImgFile) {
        String fileUrl = "";
        if (!profileImgFile.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadFile(profileImgFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        TrainerRequestDTO dto = TrainerRequestDTO.builder()
                                .trainerId(member.getUserId())
                                .trainerPw(member.getUserPw())
                                .trainerName(member.getUserName())
                                .trainerTel(member.getUserTel())
                                .profileImg(fileUrl)
                                .trainerInfo(trainerRequestDTO.getTrainerInfo())
                                .build();

        Long trainerNo = trainerRepository.save(dto.toEntity()).getNo();

  /*      if(trainerNo != null){
            memberRepository.deleteById(member.getNo());
        }*/

        return trainerNo;
    }
}
