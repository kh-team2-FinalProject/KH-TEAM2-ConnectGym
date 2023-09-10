package com.khteam2.connectgym.order;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.order.dto.OrderDetailDto;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final LessonRepository lessonRepository;

    //리뷰쓰기에 넘겨줄 정보
    @Transactional
    public OrderDetailDto findOrderDetail(Long orderDetailNo) {
        OrderDetail od = orderDetailRepository.findById(orderDetailNo).orElse(null);

        OrderDetailDto odd = OrderDetailDto.builder()
            .no(od.getNo())
            .lesson(od.getLesson())
            .build();

        return odd;
    }

    //레슨별 누적 수강생
    public int findTotalOrderCountByLessonNo(Long lessonNo) {
        return orderDetailRepository.findTotalOrderCountByLessonNo(lessonNo);
    }

    //누적 수강생 Top3
    public List<TrainerResponseDTO> findTop3Trainer() {
        int limit = 3; // 상위 3개의 트레이너를 얻고자 할 때
        List<TrainerResponseDTO> dtoList = new ArrayList<>();

        List<Object[]> trainerList = orderDetailRepository.findCountGroupByTrainer();

        for (int i = 0; i < Math.min(limit, trainerList.size()); i++) {
            Object[] result = trainerList.get(i);
            Trainer trainer = (Trainer) result[0];
            TrainerResponseDTO dto = new TrainerResponseDTO(trainer);
            Lesson lesson = lessonRepository.findByTrainerNo(trainer.getNo()).orElse(null);

            dto.setTrainerCategory(lesson.getCategory());

            dtoList.add(dto);
        }

        return dtoList;

    }




}
