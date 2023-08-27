package com.khteam2.connectgym.room;

import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.enroll.EnrollRepository;
import com.khteam2.connectgym.room.dto.RoomRequest;
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

    private final EnrollRepository enrollRepository;
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

    // 룸 입장을 위한 key check
    public boolean enterKeyCheck(Long enrollNo) {
        boolean check = false;

        EnrollDetail ed = enrollRepository.findById(enrollNo).orElse(null);
        Room room = roomRepository.findRoomKey(enrollNo);
        try {
            if (ed.getEnrollKey().equals(room.getRoomKey())) {
                check = true;
            } else if(room.getRoomKey() == null){
                check = false;
            }
        } catch (Exception e) {

        }
        return check;
    }

    // 룸 입장을 위한 세션 생성
    public String initializeSession(Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return session.getSessionId();
    }

    /*public String initializeSession(String lessonRoomName)
        throws OpenViduJavaClientException, OpenViduHttpException {

        SessionProperties properties = new SessionProperties.Builder()
            .customSessionId(lessonRoomName)
            .build();

        Session session = openvidu.createSession(properties);
        return session.getSessionId();
    }*/

    // 룸 입장
  /*  public String createConnection(String sessionId, Map<String, Object> params) throws Exception {

        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return null;
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return connection.getToken();
    }*/

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
