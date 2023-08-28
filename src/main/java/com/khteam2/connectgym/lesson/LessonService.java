package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;

    public Lesson getLesson(Long lessonNo) {
        System.out.println("겟레슨 서비스 호출");
        return lessonRepository.findById(lessonNo).orElse(null);
    }

    //레슨 생성
    @Transactional
    public Long createLesson(LessonResponseDTO lessonResponseDTO) {
        Lesson lesson = new Lesson();

        lesson.setTitle(lessonResponseDTO.getTitle());
        lesson.setTitleCode(generateTitleCode());
        lesson.setTrainer(lessonResponseDTO.getTrainer());
        lesson.setPrice(lessonResponseDTO.getPrice());
        lesson.setCategory(lessonResponseDTO.getCategory());
        lesson.setLesson_info(lessonResponseDTO.getLesson_info());
        lesson.setStart_date(lessonResponseDTO.getStart_date());
        lesson.setEnd_date(lessonResponseDTO.getEnd_date());
        lesson.setLesson_img(lessonResponseDTO.getLesson_img());

        lessonRepository.save(lesson);
        return lesson.getNo();
    }

    //레슨 타이틀코드 생성(룸 아이디)
    synchronized String generateTitleCode() {
        char titleCodeChar = 'A';
        int titleCodeNo = 0;

        int counter = titleCodeNo + 1;

        titleCodeNo++;
        if (titleCodeNo > 9999) {
            titleCodeNo = 1;
            titleCodeChar = (char) (titleCodeChar + 1);
            if (titleCodeChar > 'Z') {
                throw new IllegalStateException("Exceeded available prefixes.");
            }
        }

        return String.format("%c%04d", titleCodeChar, titleCodeNo);
    }

    //모든 레슨 가져오기
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
}
