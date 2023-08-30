package com.khteam2.connectgym.trainer;


import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.member.dto.MemberLoginRequestDto;
import com.khteam2.connectgym.member.dto.MemberLoginResponseDto;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                                MultipartFile profileImgFile, MultipartFile[] licenseImgFiles) {
        String fileUrl = "";
        //프로필사진
        if (!profileImgFile.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadProfileFile(profileImgFile, member.getUserId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //자격증사진
        if (licenseImgFiles.length != 0) {
            Licenses licenses = new Licenses();

            try {
                for (MultipartFile file : licenseImgFiles) {
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

    public MemberLoginResponseDto trainerLogin(MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = MemberLoginResponseDto.builder()
            .success(false)
            .build();

        if (requestDto == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        String id = requestDto.getId();
        String password = requestDto.getPassword();

        if (id == null || id.isBlank() || password == null || password.isBlank()) {
            responseDto.setMessage("ID 또는 비밀번호가 비어있습니다.");
            return responseDto;
        }

        Trainer trainer = this.trainerRepository.findByTrainerId(id);

        if (trainer == null || !trainer.getTrainerPw().equals(password)) {
            responseDto.setMessage("ID 또는 비밀번호를 확인해 주시기 바랍니다.");
            return responseDto;
        }

        responseDto.setSuccess(true);
        responseDto.setMemberNo(trainer.getNo());
        responseDto.setMemberClass(MemberClass.TRAINER);

        return responseDto;
    }
}
