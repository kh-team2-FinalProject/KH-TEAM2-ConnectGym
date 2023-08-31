package com.khteam2.connectgym.trainer;


import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final LicenseRepository licenseRepository;
    private final S3Uploader s3Uploader;

    //중복 검사 메소드
    public boolean validateDuplicate(String id){
        boolean check= false;
         List<Trainer> findTrainers = trainerRepository.findTrainerId(id);

        if(findTrainers.isEmpty()){
            check=true;
        }
        return check;
    }

    @Transactional
    public Long registerTrainer(TrainerRequestDTO trainerRequestDTO, Member member,
                                  MultipartFile profileImgFile,MultipartFile[] licenseImgFiles) {
       //중복검사
        boolean check = validateDuplicate(member.getUserId());
        if(!check){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

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
            try {
                for(MultipartFile file: licenseImgFiles){
                    String licenseImgUrl = s3Uploader.uploadProfileFile(file, member.getUserId());
                    License license = new License();
                    license.setLicenseImg(licenseImgUrl);
                    trainerRequestDTO.addLicense(license);
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
            .licenseList(trainerRequestDTO.getLicenseList())
            .profileImg(fileUrl)
            .trainerInfo(trainerRequestDTO.getTrainerInfo())
            .build();

        Trainer trainer = trainerRepository.save(dto.toEntity());

        /*for(License val :trainer.getLicenseList() ){
            licenseRepository.save(val);
        }*/

        return trainer.getNo();
    }
}
