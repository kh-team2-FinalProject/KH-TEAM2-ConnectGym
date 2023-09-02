package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.follow.dto.FollowForTrainerResponseDTO;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.trainer.dto.TrainerRequestDTO;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class TrainerController {

    private final MemberRepository memberRepository;
    private final TrainerService trainerService;
    private final FollowService followService;

    @GetMapping("/mypage/convertTrainer")
    public String convertTrainer(Model model){
        model.addAttribute("bannerTitle", "convert");
        return "mypage/convertToTrainerAccount";
    }

    @PostMapping("/mypage/convertTrainer")
    public String convertTrainer(Model model, TrainerRequestDTO trainerRequestDTO, HttpSession session,
                                 @RequestParam("profileImgFile") MultipartFile profileImgFile,
                                 @RequestParam("licenseImgFiles") MultipartFile[] licenseImgFiles){
        model.addAttribute("bannerTitle", "convert");

        Long userNo = (Long)session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        Member member = memberRepository.findById(userNo).orElse(null);

        //트레이너로 등록
        trainerService.registerTrainer(trainerRequestDTO, member, profileImgFile, licenseImgFiles);

        return "redirect:/mypage";
    }

    //트레이너 상세 페이지 수정 중
    @GetMapping(value = "/trainerDetail/{trainerNo}")
    public String trainerDetail(@PathVariable Long trainerNo, HttpSession session,Model model) {

        //트레이너 정보
        TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

        //트레이너 팔로우 수
        int followCount = followService.followCount(trainerNo);

        //로그인사용자가 트레이너 팔로우 했는지 확인
        Long userNo = (Long)session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        Boolean isFollow = followService.followCheck(userNo,trainerNo);


        FollowForTrainerResponseDTO followTrainerResponseDTO = FollowForTrainerResponseDTO.builder()
            .trainerFollowCnt(followCount)
            .followStatus(isFollow)
            .build();

        model.addAttribute("trainer", trainerResponseDTO);
        model.addAttribute("followInfo", followTrainerResponseDTO);

        return "detailOrCrud/trainerDetail";
    }


    //트레이너 페이지
    @GetMapping("/trainer/enrollMemberList")
    public String enrollMemberList(HttpSession session,Model model){

        // 등록강좌 불러오기
        LessonResponseDTO registered = trainerService.registered(session);


        if(registered.getErrorMsg().equals("NotFound")){

            model.addAttribute("lesson","NotFound");
        }else {
            model.addAttribute("lesson",registered);
        }

        return "trainerView";
    }



}
