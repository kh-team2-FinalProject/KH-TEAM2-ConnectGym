package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.dietlist.model.FoodInsertRequestDto;
import com.khteam2.connectgym.dietlist.model.FoodInsertResponseDto;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class DietListApiController {
    private final FoodService foodService;

    @PostMapping(value = "/api/dietList/insertFood")
    public ResponseEntity<?> insert(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        FoodInsertRequestDto requestDto) {
        FoodInsertResponseDto responseDto = this.foodService.insertFood(requestDto, loginMemberNo);

        if (!responseDto.isSuccess()) {
            return ResponseEntity.badRequest().body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }
}
