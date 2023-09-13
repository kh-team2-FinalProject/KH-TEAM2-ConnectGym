package com.khteam2.connectgym.lesson;

import com.khteam2.connectgym.lesson.dto.LessonRequestDTO;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {
    private final LessonRepository lessonRepository;
    private final TrainerRepository trainerRepository;
    private final S3Uploader s3Uploader;

    public LessonResponseDTO getLessonOne(Long lessonNo) {
        System.out.println("겟레슨 서비스 호출");
        //lessonNO로 검색한 엔티티결과 가 null이 아닌경우 DTO화 해서 반환
        return new LessonResponseDTO(Objects.requireNonNull(lessonRepository.findById(lessonNo).orElse(null)));
    }

    //레슨 생성
    @Transactional
    public Long createLesson(Long trainerNo, LessonRequestDTO lessonRequestDTO, MultipartFile file) {
        Trainer trainer = trainerRepository.findById(trainerNo).orElse(null);

        lessonRequestDTO.setTrainer(trainer);

        lessonRequestDTO.setTitleCode(generateTitleCode());

        String fileUrl = "";
        if (!file.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadLessonFile(file, lessonRequestDTO.getTitleCode());
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

    public Lesson getLessonById(Long lessonNo) {
        return lessonRepository.findById(lessonNo).orElseThrow(
            () -> new RuntimeException("LessonNo: " + lessonNo + "에 해당하는 레슨을 찾을 수 없습니다."));
    }

    /////getLessonOne 함수랑 중복?

    public int getTotalPages(int itemsPerPage, List<Lesson> list) {
        int totalItems = list.size();
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    public List<Lesson> getDataForPage(int pageNumber, int itemsPerPage,
                                       List<Lesson> lessonList) {
        List<Lesson> dataForPage = new ArrayList<>();

        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, lessonList.size());

        for (int i = startIndex; i < endIndex; i++) {
            dataForPage.add(lessonList.get(i));
        }
        return dataForPage;
    }

    public List<Lesson> viewCategoryList(List<Lesson> lessonList, Integer category) {
        List<Lesson> list = new ArrayList<>();
        if (category == 0) {
            return lessonList;
        } else {
            for (int i = 0; i < lessonList.size(); i++) {
                if (lessonList.get(i).getCategory() == category) {
                    list.add(lessonList.get(i));
                }
            }
        }
        return list;
    }

    @Transactional
    public void updateLesson(Long trainerNo, LessonRequestDTO lessonRequestDTO, MultipartFile file) {
        Trainer trainer = trainerRepository.findById(trainerNo).orElse(null);
        lessonRequestDTO.setTrainer(trainer);

        String fileUrl = "";

        if (!file.isEmpty()) {
            try {
                fileUrl = s3Uploader.uploadLessonFile(file, lessonRequestDTO.getTitleCode());
                lessonRequestDTO.setLesson_img(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            lessonRequestDTO.setLesson_img(Objects.requireNonNull(lessonRepository.findByTrainerNo(trainerNo).orElse(null)).getLesson_img());

        }


        lessonRepository.save(lessonRequestDTO.toEntity());
    }
}
