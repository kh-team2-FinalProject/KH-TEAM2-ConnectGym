package com.khteam2.connectgym.trainer;

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

@Controller
@RequiredArgsConstructor
public class TrainerController {

    private final MemberRepository memberRepository;
    private final TrainerService trainerService;


    @GetMapping("/convertTrainer")
    public String convertTrainer(Model model){
        model.addAttribute("bannerTitle", "convert");
        return "mypage/convertToTrainerAccount";
    }

    @PostMapping("/convertTrainer")
    public String convertTrainer(Model model, TrainerRequestDTO trainerRequestDTO,
                                 @RequestParam("profileImgFile") MultipartFile profileImgFile,
                                 @RequestParam("licenseImgFiles") MultipartFile[] licenseImgFiles){
        model.addAttribute("bannerTitle", "convert");

        Member member = memberRepository.findById(3L).orElse(null);

        //트레이너로 등록 후 해당 멤버는 삭제되는 서비스
        trainerService.registerTrainer(trainerRequestDTO, member, profileImgFile, licenseImgFiles);

        return "redirect:/mypage";
    }

    @GetMapping(value = "/trainerDetail/{trainerNo}")
    public String trainerDetail(@PathVariable Long trainerNo, Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "trainer detail");

        TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

        model.addAttribute("trainer", trainerResponseDTO);

        return "detailOrCrud/trainerdetail";
    }


}
