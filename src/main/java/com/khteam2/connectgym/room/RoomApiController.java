package com.khteam2.connectgym.room;


import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.room.dto.RoomRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/room")
public class RoomApiController {

    private static final Logger logger = LoggerFactory.getLogger(RoomApiController.class);

    private final RoomService roomSerivce;

    @GetMapping("/checkEnroll/{enrollNo}")
    public boolean checkEnroll(@PathVariable Long enrollNo){
        System.out.println("체크인롤 컨트롤러 호출 : " + enrollNo);
        return roomSerivce.enterKeyCheck(enrollNo);
    }

    // 룸 입장을 위한 세션 생성
	@PostMapping("/enter/id")
	public ResponseEntity<String> initializeSession(@RequestBody(required = false) RoomRequest roomRequest)
			throws OpenViduJavaClientException, OpenViduHttpException {
        String sessionId = roomSerivce.initializeSession(roomRequest.getRoomName());
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


}
