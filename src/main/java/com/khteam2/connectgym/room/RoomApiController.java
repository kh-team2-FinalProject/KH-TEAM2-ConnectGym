package com.khteam2.connectgym.room;

import io.openvidu.java.client.OpenVidu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoomApiController {

    private OpenVidu openvidu;
}
