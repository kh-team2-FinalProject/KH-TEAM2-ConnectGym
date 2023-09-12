package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.chat_test.ChatroomService;
import com.khteam2.connectgym.chat_test.dto.ChatroomDTO;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.lesson.LessonService;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.like.LikeService;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.order.OrderDetailService;
import com.khteam2.connectgym.review.ReviewService;
import com.khteam2.connectgym.review.dto.ReviewResponseListDto;
import com.khteam2.connectgym.trainer.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainerOnly")
public class TrainerOnlyController {
    private static final Logger logger = LoggerFactory.getLogger(TrainerOnlyController.class);

    private final TrainerService trainerService;
    private final TrainerOnlyService trainerOnlyService;
    private final ChatroomService chatroomService;
    private final FollowService followService;
    private final LikeService likeService;
    private final OrderDetailService orderDetailService;
    private final LessonService lessonService;
    private final ReviewService reviewService;

    ////////////////////////////////////////////////////////
    @GetMapping("/mypage")
    public String myPageT(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                          @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                          Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("bannerTitle", "my page");
        if (loginMemberClass == null) {

            // 로그인되어 있지 않은 경우
            redirectAttributes.addFlashAttribute("message", "로그인 해주세요.");
            return "redirect:/";

        } else if (loginMemberClass == MemberClass.TRAINER) {
            // 트레이너 회원 로그인 된 경우
            TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

            //트레이너 번호로 레슨 번호 찾음
            Long lessonNo = trainerOnlyService.findLessonNoByTrainerNo(trainerNo);

            //트레이너가 만든 레슨 있을 때
            if (lessonNo != null) {

                //찜된 수
                int likeCount = likeService.likeCount(lessonNo);
                model.addAttribute("likeCount", likeCount);

                //팔로워 수
                int followCount = followService.followCount(trainerNo);
                model.addAttribute("followCount", followCount);

                //누적 수강생 수
                int orderCount = orderDetailService.findTotalOrderCountByLessonNo(lessonNo);
                model.addAttribute("orderCount", orderCount);

                //레슨 수강하는 회원 목록
                TrainerEnterRoomResponseDto trainerEnterRoomDto = trainerOnlyService.enrollMemList(lessonNo, trainerNo);
                model.addAttribute("trainerEnterRoom", trainerEnterRoomDto);

                //개설 강좌 정보
                LessonResponseDTO lessonInfo = lessonService.getLessonOne(lessonNo);
                model.addAttribute("lessonInfo", lessonInfo);

                //채팅룸
                List<ChatroomDTO> chatroomList = chatroomService.searchMyMemberChatroomList(trainerNo);
                model.addAttribute("chatroomList", chatroomList);

                //리뷰
                ReviewResponseListDto trainerReview = reviewService.trainerReview(trainerNo);
                model.addAttribute("trainerReview", trainerReview);


                model.addAttribute("trainer", trainerResponseDTO);

                return "trainerOnly/myDashboard"; // 트레이너 대시보드로 이동
            } else {
                //레슨 없을 때
                model.addAttribute("errorMsg", "레슨을 찾을 수 없습니다.");
            }
        }
        return "redirect:/mypage";
    }

    @GetMapping("/mypage/liked")
    public String liked(Model model, HttpSession session) {
        try {
            model.addAttribute("bannerTitle", "liked");
            TrainerResponseDTO trainerSession = trainerService.sessionT(session);
            //세션에서 트레이너 번호 가져옴
            long trainerNo = trainerSession.getTrainerNo();

            //트레이너 번호로 레슨 번호 찾음
            Long lessonNo = trainerOnlyService.findLessonNoByTrainerNo(trainerNo);

            //트레이너가 만든 레슨 있을 때
            if (lessonNo != null) {
                List<MemberResponseDTO> likedMembers = likeService.likedList(lessonNo);
                model.addAttribute("likedMembers", likedMembers);
            } else {
                //레슨 없을 때
                model.addAttribute("errorMsg", "레슨을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            logger.error("TOC liked() 에러" + e.getMessage());
            e.printStackTrace();
        }
        return "trainerOnly/liked";
    }

    //내가 등록한 강좌 정보
    @GetMapping("/mypage/myLesson")
    public String myLessonT(Model model,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo) {
        LessonResponseDTO registered = trainerOnlyService.registered(trainerNo);

        model.addAttribute("bannerTitle", "my lesson");
        if (registered.getErrorMsg().equals("NotFound")) {
            model.addAttribute("lesson", null);
        } else {
            model.addAttribute("lesson", registered);
        }

        TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);
        model.addAttribute("trainer", trainerResponseDTO);
        return "trainerOnly/myLesson";
    }

    // 회원 불러오기
    @GetMapping("/mypage/memberList/{lessonNo}")
    public String memberList(@PathVariable Long lessonNo, Model model,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo) {
        model.addAttribute("bannerTitle", "my lesson");
        // 멤버 정보
        TrainerEnterRoomResponseDto trainerEnterRoomDto = trainerOnlyService.enrollMemList(lessonNo, trainerNo);
        model.addAttribute("trainerEnterRoom", trainerEnterRoomDto);

        return "trainerOnly/myMemberList";
    }

    // 룸 입장(없을 시 생성, 있을 시 상태 ACTIVE)
    @ResponseBody
    @PostMapping("/checkRoom")
    public TrainerRoomResponseDto checkRoom(@RequestBody TrainerEnterRoomRequestDto trainerEnterRoomRequestDto) {
        System.out.println("titleCode = " + trainerEnterRoomRequestDto.getTitleCode());
        System.out.println("enrollKey = " + trainerEnterRoomRequestDto.getEnrollKey());
        TrainerRoomResponseDto roomResponseDto
            = trainerOnlyService.createOrUptedaRoom(trainerEnterRoomRequestDto.getTitleCode(),
            trainerEnterRoomRequestDto.getEnrollKey());
        return roomResponseDto;
    }

    //팔로우
    @GetMapping("/mypage/followed")
    public String followed(Model model, HttpSession session) {
        model.addAttribute("bannerTitle", "followed");

        TrainerResponseDTO trainer = trainerService.sessionT(session);
        List<MemberResponseDTO> followed = followService.followList(trainer.getTrainerNo());
        model.addAttribute("followed", followed);

        return "trainerOnly/followed";
    }

    @GetMapping("/mypage/messages")
    public String messages(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                           @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                           Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("bannerTitle", "messages");
        if (loginMemberClass == null) {
            // 로그인되어 있지 않은 경우
            redirectAttributes.addFlashAttribute("message", "로그인 해주세요.");
            return "redirect:/";

        } else if (loginMemberClass == MemberClass.TRAINER) {
            // 트레이너 회원 로그인 된 경우
            TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

            //채팅방 목록 보여주기
            List<ChatroomDTO> chatroomList = chatroomService.searchMyMemberChatroomList(trainerNo);
            model.addAttribute("trainer", trainerResponseDTO);
            model.addAttribute("chatroomList", chatroomList);
            return "trainerOnly/messages"; // 트레이너 마이페이지로 이동
        } else {
            return "redirect:/mypage";
        }
    }


    @GetMapping("/mypage/trainerInfo")
    public String trainerMyInfo(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                                @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                                Model model, RedirectAttributes redirectAttributes) {

        model.addAttribute("bannerTitle", "my info");
        if (loginMemberClass == null) {
            // 로그인되어 있지 않은 경우
            redirectAttributes.addFlashAttribute("message", "로그인 해주세요.");
            return "redirect:/";

        } else if (loginMemberClass == MemberClass.TRAINER) {
            // 트레이너 회원 로그인 된 경우
            model.addAttribute("bannerTitle", "Trainer Info");
            TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);
            System.out.println("trainerResponseDTO = " + trainerResponseDTO);
            model.addAttribute(trainerResponseDTO);

            return "trainerOnly/trainerInfo";

        } else {
            return "redirect:/mypage";
        }
    }

    //트레이너 정보수정 페이지
    @GetMapping("/mypage/updateInfo")
    public String trainerUpdateInfo(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                                    @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (loginMemberClass == null) {
            // 로그인되어 있지 않은 경우
            redirectAttributes.addFlashAttribute("message", "로그인 해주세요.");
            return "redirect:/";

        } else if (loginMemberClass == MemberClass.TRAINER) {
            // 트레이너 회원 로그인 된 경우
            model.addAttribute("bannerTitle", "Update Info");
            TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);
            model.addAttribute("trainerDTO", trainerResponseDTO);

            return "trainerOnly/updateInfo";
        } else {
            return "redirect:/mypage";
        }
    }

    //트레이너 업데이트

    @PostMapping("/mypage/trainerUpdate")
    public String trainerUpdate(TrainerRequestDTO trainerRequestDTO,
                                @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,

                                @RequestParam("profileImgFile") MultipartFile profileImgFile,
                                @RequestParam("licenseImgFiles") MultipartFile[] licenseImgFiles) {


        trainerService.updateTrainer(loginMemberNo, trainerRequestDTO, profileImgFile, licenseImgFiles);


        return "detailOrCrud/updateComplete";
    }


    @GetMapping("/mypage/updateProfile")
    private String updateProfileInfo(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                                     Model model) {
        model.addAttribute("bannerTitle", "Update Profile");

        TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);
        model.addAttribute("trainerDTO", trainerResponseDTO);

        return "trainerOnly/updateProfile";

    }

    @PostMapping("/mypage/updateProfile")
    public String updateProfile(TrainerRequestDTO trainerRequestDTO,
                                @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
                                @RequestParam("profileImgFile") MultipartFile profileImgFile) {


        trainerService.updateProfile(loginMemberNo, trainerRequestDTO, profileImgFile);


        return "redirect:/trainerOnly/mypage";
    }

    @GetMapping("mypage/updatePassword")
    public String updatePassword(Model model) {
        model.addAttribute("bannerTitle", "Update Password");
        return "/trainerOnly/updatePassword";
    }

    @PostMapping("mypage/updatePassword")
    public String updatePasswordProcess(TrainerRequestDTO trainerRequestDTO,
                                        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo) {

        trainerService.updatePassword(loginMemberNo, trainerRequestDTO);

        return "detailOrCrud/updateComplete";
    }
}


