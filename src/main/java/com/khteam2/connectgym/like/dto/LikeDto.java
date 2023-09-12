package com.khteam2.connectgym.like.dto;

import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDto {
    private List<LessonResponseDTO> myLikeList;
    private int likeCnt;
    private boolean likeStatus;
    private String keyword; // 검색용
}

