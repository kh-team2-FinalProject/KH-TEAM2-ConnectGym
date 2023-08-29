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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long registerTrainer(TrainerRequestDTO trainerRequestDTO, Member member,
                                  MultipartFile profileImgFile,MultipartFile[] licenseImgFiles) {
        String fileUrl = "";
        //프로필사진
        if (!profileImgFile.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadProfileFile(profileImgFile,member.getUserId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //자격증사진
        if (licenseImgFiles.length != 0) {
            Licenses licenses = new Licenses();

            try {
                for(MultipartFile file: licenseImgFiles){
                    licenses.setLicenseImg(s3Uploader.uploadProfileFile(file, member.getUserId()));
                    trainerRequestDTO.addLicense(licenses);
                }

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

        return trainerNo;
    }
}
