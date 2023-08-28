package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;

    public Lesson getLesson(Long lessonNo) {
        System.out.println("겟레슨 서비스 호출");
        return lessonRepository.findById(lessonNo).orElse(null);
    }

    @Transactional
    public Long createLesson(LessonResponseDTO lessonResponseDTO) {
        Lesson lesson = new Lesson();
        lesson.setNo(lessonResponseDTO.getNo());
        lesson.setTitle(lessonResponseDTO.getTitle());
        lesson.setTitleCode(lessonResponseDTO.getTitleCode());
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
}
