package com.khteam2.connectgym.room;

import com.khteam2.connectgym.room.dto.RoomRequest;
import io.openvidu.java.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomApiController.class);

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;


    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    // 룸 입장을 위한 세션 생성
    public String initializeSession(RoomRequest roomRequest)
        throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = new SessionProperties.Builder()
            .customSessionId(roomRequest.getRoomName())
            .build();
        Session session = openvidu.createSession(properties);
        System.out.println("service session = " + session.toString());
        return session.getSessionId();
    }

    // 룸 입장
    public String createConnection(String sessionId, RoomRequest roomRequest)
        throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return null;
        }

        ConnectionProperties properties = new ConnectionProperties.Builder()
            .data(roomRequest.getRoomKey()) // Assuming roomKey corresponds to data
            .build();
        Connection connection = session.createConnection(properties);
        return connection.getToken();
    }

}
