package com.khteam2.connectgym.member;

import com.khteam2.connectgym.chat_test.ChatroomService;
import com.khteam2.connectgym.chat_test.dto.ChatroomDTO;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.like.LikeService;
import com.khteam2.connectgym.member.dto.MemberDto;
import com.khteam2.connectgym.member.dto.MemberResponseDto;
import com.khteam2.connectgym.order.OrderDetailService;
import com.khteam2.connectgym.order.dto.OrderDetailDto;
import com.khteam2.connectgym.review.ReviewService;
import com.khteam2.connectgym.review.dto.MyReviewResponseDto;
import com.khteam2.connectgym.trainer.TrainerService;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {

    private final TrainerService trainerService;
    private final MemberService memberService;
    private final MailSendService mailService;
    private final FollowService followService;
    private final LikeService likeService;
    private final ChatroomService chatroomService;
    private final OrderDetailService orderDetailService;
    private final ReviewService reviewService;

    @GetMapping(value = "/user/login")
    public String login(
        Model model,
        HttpServletRequest request,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo
    ) {
        if (loginMemberNo != null) {
            model.addAttribute("message", "이미 로그인되어 있는 상태입니다.");
            return "redirect:/";
        }

        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/user/")) {
            // URI에 "/user/"을 포함하는 경우 referer 객체를 비웁니다.
            referer = null;
        }

        model.addAttribute("requestUrl", referer);

        return "content/login";
    }

    @GetMapping(value = "/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_NO);
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_CLASS);

        return "redirect:/";
    }

    @GetMapping(value = "/user/join")
    public String join() {
        return "content/join";
    }

    // 회원가입 버튼 클릭 시 실행
    @PostMapping("/user/joinProcess")
    public String saveUser(MemberDto memberDTO) {
        memberService.createMember(memberDTO);
        return "redirect:/user/login";
    }

    //    // email 인증시 사용
    @GetMapping("/mailCheck")
    @ResponseBody
    public String mailCheck(String email) {
        System.out.println("email 들어오는지 체크중" + email);
        return mailService.joinEmail(email);
    }

    //  로컬호스트일 때의 urlmapping
    @RequestMapping(value = "/connectgym", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code,
                             HttpSession session) {
        // 인가코드 받는 부분 // 출력 테스트
        System.out.println("###########" + code);

        // 토큰 받는 부분
        String access_Token = memberService.getAccessToken(code);
        System.out.println(access_Token);

        // 실제 정보를 출력하는 메서드 호출하기
        HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);

        // 카카오 정보를 이용하여 users 테이블의 email과 비교하여 회원정보가 있는지 확인하기
        String kakaoemail = (String) userInfo.get("email");
        HashMap<String, Object> m = memberService.findMemberByEmail(kakaoemail);
        HashMap<String, Object> t = trainerService.findTrainerByEmail(kakaoemail);

        // 출력 테스트용
        System.out.println("############" + kakaoemail);

        // 응답받아오면 카카오 정보 비교하여 있으면 main으로 없으면 회원가입으로 return
        if (m == null && t == null) {
            session.setAttribute("kakaoEmail", kakaoemail);

            // 휴대폰 번호 받아올 수 있을 때 그 정보도 추가해야함.

            return "redirect:/user/join";
        } else {
            System.out.println("==============member: " + m);
            System.out.println("==============trainer: " + t);
            if (m != null) {
                // users table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, m.get("user_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.MEMBER);
            }
            if (t != null) {
                // trainer table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, t.get("trainer_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.TRAINER);
            }

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/connectgym.store", method = RequestMethod.GET)
    public String kakaoLogindomain(@RequestParam(value = "code", required = false) String code,
                                   HttpSession session) {
        // 인가코드 받는 부분 // 출력 테스트
        System.out.println("###########" + code);

        // 토큰 받는 부분
        String access_Token = memberService.getAccessToken(code);
        System.out.println(access_Token);

        // 실제 정보를 출력하는 메서드 호출하기
        HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);

        // 카카오 정보를 이용하여 users 테이블의 email과 비교하여 회원정보가 있는지 확인하기
        String kakaoemail = (String) userInfo.get("email");
        HashMap<String, Object> m = memberService.findMemberByEmail(kakaoemail);
        HashMap<String, Object> t = trainerService.findTrainerByEmail(kakaoemail);

        // 출력 테스트용
        System.out.println("############" + kakaoemail);

        // 응답받아오면 카카오 정보 비교하여 있으면 main으로 없으면 회원가입으로 return
        if (m == null && t == null) {
            session.setAttribute("kakaoEmail", kakaoemail);

            // 휴대폰 번호 받아올 수 있을 때 그 정보도 추가해야함.

            return "redirect:/user/join";
        } else {
            System.out.println("==============member: " + m);
            System.out.println("==============trainer: " + t);
            if (m != null) {
                // users table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, m.get("user_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.MEMBER);
            }
            if (t != null) {
                // trainer table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, t.get("trainer_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.TRAINER);
            }

            return "redirect:/";
        }
    }

    // id pw 찾기
    @GetMapping("/user/findId")
    public String findIDPage() {
        return "content/findIDPage";
    }

    @GetMapping("/user/findPw")
    public String findPWPage() {
        return "content/findPWPage";
    }

    // ======= 마이페이지 =======
    @GetMapping("/mypage")
    public String myPage() {

        return "redirect:/mypage/myLessonList";
    }

    // 1) 대시보드
    @GetMapping("/mypage/myDashboard")
    public String myDashboard(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "my dashboard");

        MemberResponseDto member = memberService.sessionMem(session);

        model.addAttribute("member", member);
        return "mypage/dashboard";
    }

    // 2) 내 수강목록
    @GetMapping("/mypage/myLessonList")
    public String myLesson(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "MY LESSON");

        MemberResponseDto member = memberService.sessionMem(session);
        model.addAttribute("member", member);

        return "mypage/myLessonList";
    }

    // 3) 팔로잉
    @GetMapping("/mypage/myFollowing")
    public String myFolloing(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "following");

        MemberResponseDto member = memberService.sessionMem(session);

        List<TrainerResponseDto> following = followService.followingList(member.getNo());

        model.addAttribute("following", following);

        return "mypage/following";
    }

    // 4) 찜
    @GetMapping("/mypage/myLike")
    public String myLikes(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "like");

        MemberResponseDto member = memberService.sessionMem(session);

        List<LessonResponseDTO> likes = likeService.likingList(member.getNo());

        model.addAttribute("likes", likes);

        return "mypage/like";
    }

    //  5) 채팅방 리스트
    @GetMapping("/mypage/myChatroomList")
    public String myChatroomList(Model model, HttpSession session) {
        // 배너 타이틀
        model.addAttribute("bannerTitle", "messages");
        MemberResponseDto member = memberService.sessionMem(session);
        List<ChatroomDTO> chatroomList = chatroomService.searchMyTrainerChatroomList(
            member.getNo());
        model.addAttribute("chatroomList", chatroomList);
        return "mypage/messages";
    }

    // 6-2) 주문내역 > 리뷰 쓰기 ( 6-1) 주문내역 : orderController)
    @GetMapping("/mypage/writeReview/{detailNo}")
    public String writeReview(@PathVariable Long detailNo, Model model) {
        OrderDetailDto odd = orderDetailService.findOrderDetail(detailNo);

        model.addAttribute("bannerTitle", "write review");
        model.addAttribute("orderDetail", odd);
        return "mypage/review/writeReview";
    }

    // 7) 내 리뷰 관리
    @GetMapping("/mypage/myReviewList")
    public String showReview(Model model,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo) {
        model.addAttribute("bannerTitle", "review");

        List<MyReviewResponseDto> reviewList = reviewService.myReview(loginMemberNo);
        model.addAttribute("reviewList", reviewList);

        return "mypage/review/reviewList";
    }

    // 8-1) 회원정보
    @GetMapping("/mypage/myInfo")
    public String myInfo(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "my info");

        MemberResponseDto member = memberService.sessionMem(session);

        model.addAttribute("member", member);

        return "mypage/myInfo";
    }

    // 8-2)회원정보 업데이트
    @PostMapping(value = "/mypage/updateProcess")
    public String updateProcess(MemberDto memberDTO) {

        // 회원정보 수정 버튼 누르면 실행되는 컨트롤러
        // 버튼 클릭 시 회원정보 수정해주는 서비스 함수 실행
        memberService.updateMember(memberDTO);

        return "redirect:/mypage/myDashboard";
    }

    // 8-3) 회원정보 내 트레이너 전환
    @GetMapping(value = "/mypage/convertToTrainerAccount")
    public String convertAccount(Model model) {
        model.addAttribute("bannerTitle", "CONVERT TO TRAINER ACCOUNT");
        return "mypage/convertToTrainerAccount";
    }
}
