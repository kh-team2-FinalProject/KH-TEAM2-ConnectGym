package com.khteam2.connectgym.lesson;


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
    public Long createLesson(Lesson lesson) {
        lessonRepository.save(lesson);
        return lesson.getNo();
    }
}
