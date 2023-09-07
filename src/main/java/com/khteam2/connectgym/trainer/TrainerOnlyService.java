package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonRepository;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.order.Order;
import com.khteam2.connectgym.order.OrderDetail;
import com.khteam2.connectgym.order.OrderDetailRepository;
import com.khteam2.connectgym.order.OrderRepository;
import com.khteam2.connectgym.room.Room;
import com.khteam2.connectgym.room.RoomRepository;
import com.khteam2.connectgym.room.dto.RoomStatus;
import com.khteam2.connectgym.trainer.dto.TrainerEnterRoomResponseDto;
import com.khteam2.connectgym.trainer.dto.TrainerRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerOnlyService {
    //==============트레이너 룸 입장 테스트==================//
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final LessonRepository lessonRepository;
    private final RoomRepository roomRepository;

    //트레이너의 등록 레슨 불러오기
    public LessonResponseDTO registered(Long no) {

        Lesson lesson = lessonRepository.findByTrainerNo(no).orElse(null);

        if (lesson == null) {
            LessonResponseDTO lessonResponseDTO = LessonResponseDTO.builder()
                .errorMsg("NotFound")
                .build();
            return lessonResponseDTO;
        }
        LessonResponseDTO lessonResponseDTO = new LessonResponseDTO(lesson);
        lessonResponseDTO.setErrorMsg("Success");

        return lessonResponseDTO;

    }

    // 레슨 등록한 회원 목록 및 룸 접근을 위한 값 조회
    public TrainerEnterRoomResponseDto enrollMemList(Long lessonNo, Long trainerNo) {

        // 룸 코드
        Lesson lesson = lessonRepository.findById(lessonNo).orElse(null);
        String titleCode = lesson.getTitleCode();

        // 룸 키에 따른 멤버
        Map<Long, MemberResponseDTO> memberMap = new HashMap<>();

        List<OrderDetail> orderDetails = orderDetailRepository.enrollList(lessonNo);

        if (orderDetails.size() == 0) {
            TrainerEnterRoomResponseDto trainerEnterRoomDto = TrainerEnterRoomResponseDto.builder()
                .errorMsg("NotFound")
                .build();
            return trainerEnterRoomDto;
        }

        for (OrderDetail od : orderDetails) {
            Order order = orderRepository.findByOrderNo(od.getOrder().getNo()).orElse(null);

            MemberResponseDTO memberResponseDTO = new MemberResponseDTO(order.getMember());

            memberMap.put(od.getEnrollKey(), memberResponseDTO);

        }

        TrainerEnterRoomResponseDto trainerEnterRoomDto = TrainerEnterRoomResponseDto.builder()
            .titleCode(titleCode)
            .memberMap(memberMap)
            .build();


        return trainerEnterRoomDto;

    }

    //트레이너 입장 시 방이 활성화, 기존 방이 없으면 새로 만들고 있으면 기존방의 상태를 'ACTIVE' 업데이트
    @Transactional
    public TrainerRoomResponseDto createOrUptedaRoom(String titleCode, Long enrollKey) {
        OrderDetail orderDetail = orderDetailRepository.findByEnrollKey(enrollKey);

        String roomName = titleCode + "" + enrollKey;

        Room room = roomRepository.findByRoomName(roomName).orElse(null);

        if (room == null) {
            Room createRoom = Room.builder()
                .roomName(roomName)
                .roomStatus(RoomStatus.ACTIVE)
                .orderDetail(orderDetail)
                .build();

            Room saveRoom = roomRepository.save(createRoom);

            TrainerRoomResponseDto roomResponseDto = TrainerRoomResponseDto.builder()
                .no(saveRoom.getNo())
                .roomName(saveRoom.getRoomName())
                .roomStatus(saveRoom.getRoomStatus())
                .build();

            return roomResponseDto;
        } else {
            roomRepository.updateRoomStatus(RoomStatus.ACTIVE, room.getNo());

            Room existRoom = roomRepository.findByRoomName(roomName).orElse(null);

            TrainerRoomResponseDto roomResponseDto = TrainerRoomResponseDto.builder()
                .no(existRoom.getNo())
                .roomName(existRoom.getRoomName())
                .roomStatus(existRoom.getRoomStatus())
                .build();


            return roomResponseDto;
        }


    }


}
