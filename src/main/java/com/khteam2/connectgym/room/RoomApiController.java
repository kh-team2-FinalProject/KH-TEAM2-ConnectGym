package com.khteam2.connectgym.room;

import com.khteam2.connectgym.enroll.dto.EnrollResponseDto;
import com.khteam2.connectgym.room.dto.RoomRequest;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/room")
public class RoomApiController {
    private static final Logger logger = LoggerFactory.getLogger(RoomApiController.class);

    private final RoomService roomSerivce;

    // 룸 조건 검사
    @PostMapping("/checkEnroll")
    public Long checkEnroll(@RequestBody EnrollResponseDto dto) {
        return roomSerivce.roomStatusCheck(dto.getLessonTitleCode(), dto.getEnrollKey());
    }

    // 룸 세션 생성
    @PostMapping("/enter/init")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("룸이름: " + params.get("customSessionId"));
        String sessionId = roomSerivce.initializeSession(params);
        System.out.println("sessionId 이니셜라이즈 = " + sessionId);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    // 룸 입장
    @PostMapping("/enter/{sessionId}/connection")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody RoomRequest roomRequest) {
        try {
            String connectionToken = roomSerivce.createConnection(sessionId, roomRequest);

            if (connectionToken != null) {
                return new ResponseEntity<>(connectionToken, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 룸 퇴장
    @GetMapping("/exit/{sessionId}")
    public int exitRoom(@PathVariable("sessionId") String sessionId, @RequestParam String userType) {
        System.out.println("userType = " + userType);
        if (userType.equals("trainer")) {
            roomSerivce.exitRoom(sessionId);
            return 1;
        }

        return 0;
    }
}
