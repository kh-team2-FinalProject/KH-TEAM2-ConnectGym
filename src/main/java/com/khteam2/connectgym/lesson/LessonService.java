package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.lesson.dto.LessonRequestDTO;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TrainerRepository trainerRepository;
    private final S3Uploader s3Uploader;

    public Lesson getLesson(Long lessonNo) {
        System.out.println("겟레슨 서비스 호출");
        return lessonRepository.findById(lessonNo).orElse(null);
    }

    //레슨 생성
    @Transactional
    public Long createLesson(LessonRequestDTO lessonRequestDTO, MultipartFile file) {
        Trainer trainer = trainerRepository.findById(1L).orElse(null);
        lessonRequestDTO.setTrainer(trainer);

        lessonRequestDTO.setTitleCode(generateTitleCode());

        String fileUrl = "";
        if (!file.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //로그인한 세션에 트레이너 객체의 no가 있으니까
        lessonRequestDTO.setLesson_img(fileUrl);

        Lesson lesson = lessonRepository.save(lessonRequestDTO.toEntity());
        return lesson.getNo();
    }

    //레슨 타이틀코드 생성(룸 아이디)
    synchronized String generateTitleCode() {
        String latestTitleCode = lessonRepository.findLatestTitleCode().get(0);
        System.out.println("latestTitleCode = " + latestTitleCode);
        String titleCode = "";
        if (latestTitleCode == null) {
            titleCode = "A00001";
        } else {
            int numericPart = Integer.parseInt(latestTitleCode.substring(1)) + 1;
            titleCode = String.format("A%05d", numericPart);
        }

        return titleCode;
    }

    //모든 레슨 가져오기
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public String getTrainerIdFromLesson(Lesson lesson) {
        if (lesson != null) {
            Trainer trainer = lesson.getTrainer();
            if (trainer != null) {
                return trainer.getTrainerId();
            }
        }
        return null;
    }
}
