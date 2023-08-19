package com.khteam2.connectgym.room;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RoomApiController {

    private final RoomSerivce roomService;


    // 방 생성
    @PostMapping("/{teamId}")
    public ResponseEntity<BasicResponse<Long>> createRoom(@RequestBody RoomRequest roomRequest, @PathVariable Long teamId) {
        Long sessionId = roomService.createRoom(teamId, roomRequest);
        return new ResponseEntity<>(makeBasicResponse(SUCCESS, sessionId), HttpStatus.CREATED);
    }

    // 생성된 방에 입장
    @PostMapping("/{teamId}/enter/{nickName}")
    public ResponseEntity<BasicResponse<String>> enterRoom(@PathVariable Long teamId, @PathVariable String nickName) {
        String enterUser = roomService.enterRoom(teamId, nickName);
        return new ResponseEntity<>(makeBasicResponse(SUCCESS, enterUser), HttpStatus.CREATED);
    }

    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }




}
