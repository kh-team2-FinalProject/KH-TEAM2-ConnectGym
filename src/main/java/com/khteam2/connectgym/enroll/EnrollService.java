package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.enroll.dto.EnrollResponseDto;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.order.*;
import com.khteam2.connectgym.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public List<EnrollResponseDto> enrollList(MemberResponseDTO mem){
        List<EnrollResponseDto> enrolls = new ArrayList<>();
        System.out.println("mem = " + mem.getNo()+","+mem.getUserName());

        Member member = Member.builder()
            .no(mem.getNo())
            .userId(mem.getUserId())
            .userPw(mem.getUserPw())
            .userTel(mem.getUserTel())
            .userAddress(mem.getUserAddress())
            .userEmail(mem.getUserEmail())
            .build();

        List<Order> orders = orderRepository.findByMember(member);

        System.out.println("orders = " + orders.toString());

/*        if (orders == null) {
            EnrollResponseDto enrollResponseDto = EnrollResponseDto.builder()
                .errorMsg("NotFound")
                .build();


            return
        }*/

        for (Order o : orders) {
            OrderDetail orderDetail= orderDetailRepository.findByEnroll(o);

            LessonResponseDTO lessonResponseDTO =
                new LessonResponseDTO(orderDetail.getLesson());

            EnrollResponseDto enrollResponseDto = EnrollResponseDto.builder()
                .lesson(lessonResponseDTO)
                .enrollKey(orderDetail.getEnrollKey())
                .build();

            enrolls.add(enrollResponseDto);

        }

        return enrolls;

    }


}
