package com.khteam2.connectgym.trainer;


import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.dto.MemberLoginRequestDto;
import com.khteam2.connectgym.member.dto.MemberLoginResponseDto;
import com.khteam2.connectgym.order.OrderDetailRepository;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import com.khteam2.connectgym.upload.S3Uploader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final LessonRepository lessonRepository;
    private final LicenseRepository licenseRepository;
    private final S3Uploader s3Uploader;

    //중복 검사 메소드
    public boolean validateDuplicate(String id) {
        boolean check = false;
        Trainer findTrainer = trainerRepository.findByTrainerId(id);

        if (findTrainer == null) {
            check = true;
        }
        return check;
    }

    //트레이너 가입
    @Transactional
    public Long registerTrainer(TrainerRequestDTO trainerRequestDTO, Member member,
                                MultipartFile profileImgFile, MultipartFile[] licenseImgFiles) {
        //중복검사
        boolean check = validateDuplicate(member.getUserId());
        if (!check) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        String fileUrl = "";
        //프로필사진
        if (!profileImgFile.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadProfileFile(profileImgFile, member.getUserId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        TrainerRequestDTO dto = TrainerRequestDTO.builder()
            .trainerId(member.getUserId())
            .trainerPw(member.getUserPw())
            .trainerName(member.getUserName())
            .trainerTel(member.getUserTel())
            .trainerEmail(member.getUserEmail())
            /*  .licenseList(trainerRequestDTO.getLicenseList())*/
            .profileImg(fileUrl)
            .infoTitle(trainerRequestDTO.getInfoTitle())
            .infoContent(trainerRequestDTO.getInfoContent())
            .build();

        Trainer trainer = trainerRepository.save(dto.toEntity());

        //자격증사진
        if (licenseImgFiles.length != 0) {
            try {
                for (MultipartFile file : licenseImgFiles) {
                    String licenseImgUrl = s3Uploader.uploadProfileFile(file, member.getUserId());
                    License license = License.builder()
                        .licenseImg(licenseImgUrl)
                        .trainer(trainer)
                        .build();
                    licenseRepository.save(license);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return trainer.getNo();
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

    //트레이너 불러오기(레슨까지)
    public TrainerResponseDTO findOneTrainer(Long trainerNo) {
        Trainer trainer = trainerRepository.findById(trainerNo).orElse(null);

        //레슨 번호
        Lesson lesson = lessonRepository.findByTrainerNo(trainerNo).orElse(null);


        //라이선스 목록
        List<License> licenses = licenseRepository.findAllTrainerNo(trainerNo);

        TrainerResponseDTO trainerResponseDTO = TrainerResponseDTO.builder()
            .trainerNo(trainerNo)
            .trainerId(trainer.getTrainerId())
            .trainerName(trainer.getTrainerName())
            .trainerTel(trainer.getTrainerTel())
            .profileImg(trainer.getProfileImg())
            .infoTitle(trainer.getInfoTitle())
            .infoContent(trainer.getInfoContent())
            .licenses(licenses)
            .build();

        if (lesson == null) {
            trainerResponseDTO.setLessonNo(-1L);
        } else {
            trainerResponseDTO.setLessonNo(lesson.getNo());
        }

        return trainerResponseDTO;
    }

    public HashMap<String, Object> findTrainerByEmail(String email) {
        List<Trainer> TrainerList = trainerRepository.findAll();
        HashMap<String, Object> findTrainer = new HashMap<>();

        for (int i = 0; i < TrainerList.size(); i++) {
            if (email.equals(TrainerList.get(i).getTrainerEmail())) {
                findTrainer.put("trainer_no", TrainerList.get(i).getNo());
                findTrainer.put("trainer_id", TrainerList.get(i).getTrainerId());
                findTrainer.put("trainer_pw", TrainerList.get(i).getTrainerPw());
                findTrainer.put("trainer_name", TrainerList.get(i).getTrainerName());
                findTrainer.put("trainer_tel", TrainerList.get(i).getTrainerTel());
                findTrainer.put("trainer_email", TrainerList.get(i).getTrainerEmail());
                return findTrainer;
            }
        }
        return null;
    }

    private final OrderDetailRepository orderDetailRepository;

    //트레이너의 등록 레슨 불러오기
    public LessonResponseDTO registered(HttpSession session) {

        Long no = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);

        Lesson lesson = lessonRepository.findByTrainerNo(no).orElse(null);


        if (lesson == null) {
            LessonResponseDTO lessonResponseDTO = LessonResponseDTO.builder()
                .errorMsg("NotFound")
                .build();
            return lessonResponseDTO;
        }
        LessonResponseDTO lessonResponseDTO = new LessonResponseDTO(lesson);

        return lessonResponseDTO;

    }


}

