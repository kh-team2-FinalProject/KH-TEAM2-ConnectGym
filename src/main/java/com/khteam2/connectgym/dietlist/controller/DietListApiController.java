package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.dietlist.model.*;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DietListApiController {
    private final FoodService foodService;

    @GetMapping(value = "/api/dietList/findFood")
    public ResponseEntity<?> find(FoodFindRequestDto requestDto) {
        FoodFindResponseDto responseDto = this.foodService.findFood(requestDto);

        if (!responseDto.isSuccess()) {
            return ResponseEntity.badRequest().body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }

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

    @GetMapping(value = "/api/dietList/list")
    public DietListResponseDto list(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return this.foodService.dietList(loginMemberNo, date);
    }
}
