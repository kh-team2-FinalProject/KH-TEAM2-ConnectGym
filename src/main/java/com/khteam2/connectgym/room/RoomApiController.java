package com.khteam2.connectgym.room;


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

    @PostMapping("/checkEnroll")
    public boolean checkEnroll(@RequestBody(required = false) RoomRequest roomRequest){
//     boolean check = roomSerivce.checkEnroll;
        System.out.println("check 컨트롤러 동작");
        boolean check = false;
        if(roomRequest.getRoomKey().equals("1234")){
            check = true;
            return check;
        }

        return check;
    }


    // 룸 입장을 위한 세션 생성
	@PostMapping("/enter/id")
	public ResponseEntity<String> initializeSession(@RequestBody(required = false) RoomRequest roomRequest)
			throws OpenViduJavaClientException, OpenViduHttpException {
        // 여기서 roomName과 roomKey 조건 확인하는 service 호출

        String sessionId = roomSerivce.initializeSession(roomRequest);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
	}

    // 룸 입장
    @PostMapping("/enter/{sessionId}/connection")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody RoomRequest roomRequest) {
        try {
            System.out.println(sessionId);
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