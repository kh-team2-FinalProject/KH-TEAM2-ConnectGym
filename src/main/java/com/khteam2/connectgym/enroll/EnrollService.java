package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.lesson.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollService {

    private final EnrollRepository enrollRepository;
//    private final LessonRepository lessonRepository;

    public List<EnrollDetail> memLessonList(Long memId){
        return enrollRepository.memLessonList(memId);
    }


}
