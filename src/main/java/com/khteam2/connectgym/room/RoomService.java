package com.khteam2.connectgym.room;

import com.khteam2.connectgym.room.dto.RoomRequest;
import com.khteam2.connectgym.room.dto.RoomResponseDto;
import com.khteam2.connectgym.room.dto.RoomStatus;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomApiController.class);

    private final RoomRepository roomRepository;

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;


    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    //룸 상태 확인
    public Long roomStatusCheck(String titleCode, Long enrollKey) {

        // -1 : 룸 생성 전
        // 0 : 트레이너 입장 전
        // 1 : 입장 가능

        String reqRoomName = titleCode + "" + enrollKey;
        System.out.println("roomRepository reqRoomName = " + reqRoomName);

        Room room = roomRepository.findByRoomName(reqRoomName).orElse(null);

        if (room == null) {
            return -1L;
        }

        if (room.getRoomStatus().equals(RoomStatus.ACTIVE)) {
            return room.getNo();
        } else {
            return 0L;
        }

    }

    //룸 정보 불러오기
    public RoomResponseDto enterRoomInfo(Long roomNo) {
        Room room = roomRepository.findById(roomNo).orElse(null);

        RoomResponseDto roomResponseDto = RoomResponseDto.builder()
            .no(room.getNo())
            .roomName(room.getRoomName())
            .lessonTitle(room.getOrderDetail().getLesson().getTitle())
            .build();

        return roomResponseDto;
    }











    // 룸 입장을 위한 세션 생성
    public String initializeSession(Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return session.getSessionId();
    }

    public String createConnection(String sessionId, RoomRequest roomRequest)
        throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return null;
        }
        System.out.println("roomKey = " + roomRequest.getRoomKey());
        ConnectionProperties properties = new ConnectionProperties.Builder()
            .data(roomRequest.getRoomKey()) // Assuming roomKey corresponds to data
            .build();
        Connection connection = session.createConnection(properties);
        return connection.getToken();
    }


}
