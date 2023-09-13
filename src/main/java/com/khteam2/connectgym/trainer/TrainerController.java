package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.follow.dto.FollowForTrainerDto;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.review.ReviewService;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainerController {
    private final MemberRepository memberRepository;
    private final TrainerService trainerService;
    private final FollowService followService;
    private final ReviewService reviewService;

    @GetMapping("/trainerList")
    public String trainerList(Model model) {
        List<TrainerResponseDto> trainerAll = trainerService.trainerAll();

        model.addAttribute("trainerList", trainerAll);

        return "content/trainer";
    }

    @ResponseBody
    @PostMapping("/trainerList/search")
    public List<TrainerResponseDto> searchTrainer(@RequestBody(required = false) TrainerRequestDTO trainer) {
        return trainerService.searchTrainer(trainer.getKeyword());
    }


    @GetMapping("/mypage/convertTrainer")
    public String convertTrainer(Model model) {
        model.addAttribute("bannerTitle", "convert");
        return "mypage/convertToTrainerAccount";
    }

    @PostMapping("/mypage/convertTrainer")
    public String convertTrainer(Model model, TrainerRequestDTO trainerRequestDTO, HttpSession session,
                                 @RequestParam("profileImgFile") MultipartFile profileImgFile,
                                 @RequestParam("licenseImgFiles") MultipartFile[] licenseImgFiles) {
        model.addAttribute("bannerTitle", "convert");

        Long userNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        Member member = memberRepository.findById(userNo).orElse(null);

        //트레이너로 등록
        Long trainerNewNo = trainerService.registerTrainer(trainerRequestDTO, member, profileImgFile, licenseImgFiles);

        return "redirect:/mypage";
    }

    //트레이너 상세 페이지
    @GetMapping(value = "/trainerDetail/{trainerNo}")
    public String trainerDetail(@PathVariable Long trainerNo, HttpSession session, Model model) {
        //트레이너 정보
        TrainerResponseDto trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

        //리뷰 수
        int reviewCount = reviewService.trainerReviewCount(trainerNo);

        //트레이너 팔로우 수
        int followCount = followService.followCount(trainerNo);

        //로그인사용자가 트레이너 팔로우 했는지 확인
        Long userNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        Boolean isFollow = followService.followCheck(userNo, trainerNo);

        System.out.println("isFollow = " + isFollow);

        FollowForTrainerDto followTrainerResponseDTO = FollowForTrainerDto.builder()
            .trainerFollowCnt(followCount)
            .followStatus(isFollow)
            .build();

        model.addAttribute("trainer", trainerResponseDTO);
        model.addAttribute("followInfo", followTrainerResponseDTO);
        model.addAttribute("reviewCount", reviewCount);

        return "detailOrCrud/trainerDetail";
    }
}
