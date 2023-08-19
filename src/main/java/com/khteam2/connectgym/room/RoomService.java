package com.khteam2.connectgym.room;

import io.openvidu.java.client.OpenVidu;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final OpenVidu openVidu;

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    @Autowired
    public RoomService(OpenVidu openVidu ) {
        this.openVidu = new OpenVidu( OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /*
운동방 생성
 */
    public Long createRoom(Long teamId, RoomRequest roomRequest) {
        //운동방이 없는 경우에만 운동방 생성
        if (getTeamInfo(teamId).getRoom() == null) {
            Routine routine = getRoutine(roomRequest.getRoutineId());
            Team team = getTeamInfo(teamId);
            Room room = Room.createRoom(roomRequest, routine, Status.READY);
            team.setRoom(room);
            roomRepository.save(room);
            teamRepository.save(team);
            roomParticipantRepository.save(RoomParticipant.createRoomParticipant(room, roomRequest.getCreator()));
        }
        return teamId;
    }
}
