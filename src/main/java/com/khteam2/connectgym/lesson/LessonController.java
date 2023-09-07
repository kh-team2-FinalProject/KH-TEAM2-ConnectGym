package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.lesson.dto.LessonRequestDTO;
import com.khteam2.connectgym.like.LikeService;
import com.khteam2.connectgym.like.dto.LikeDto;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final LikeService likeService;

    @GetMapping(value = "/createLesson")
    public String createLesson(Model model) {

        //배너타이틀
        model.addAttribute("bannerTitle", "create");

        //폼 호출
        model.addAttribute("lessonForm", new Lesson());

        return "detailOrCrud/createLesson";
    }

    @PostMapping(value = "/createLesson")
    public String createLesson(Model model, LessonRequestDTO lessonRequestDTO,
                               @RequestParam("lessonImgFile") MultipartFile file) {

        //배너타이틀
        model.addAttribute("bannerTitle", "create lesson");

        System.out.println("lessonRequestDTO = " + lessonRequestDTO.getStart_date());
        //service save() 호출
        lessonService.createLesson(lessonRequestDTO, file);

        return "detailOrCrud/createComplete";
    }
//    레슨 다 가져오기 // 기존 mapping
//    @GetMapping("/lesson-list")
//    public String lessonList(Model model) {
//        model.addAttribute("bannerTitle", "lessons");
//
//        List<Lesson> lessonList = lessonService.getAllLessons();
//        model.addAttribute("lessonList", lessonList);
//
//        // start paging
//
//
//        return "lesson/lessonCategory";
//    }

    //레슨 다 가져오기
    @GetMapping("/lesson-list")
    public String lessonList(Model model,
                             @RequestParam(name = "category", required = false, defaultValue = "0") Integer category,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber
    ) {
        model.addAttribute("bannerTitle", "lessons");

        // ---
        int itemsPerPage = 1;   // 한 페이지에 보여질 게시글 수

        List<Lesson> lessonList = lessonService.getAllLessons(); // 모든 lesson
        lessonList = lessonService.viewCategoryList(lessonList,
            category); // 카테고리별 lesson 카테고리 0일경우 모든 lesson

        int totalPages = lessonService.getTotalPages(itemsPerPage, lessonList); // 총 페이지 수
        lessonList = lessonService.getDataForPage(pageNumber, itemsPerPage,
            lessonList); // 카테고리에 따른 한 페이지당 나오는 게시글

        model.addAttribute("lessonList", lessonList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("category", category);
        System.out.println(category);

        return "lesson/lessonCategory";
    }

    //레슨 한 개만 불러오기
    @GetMapping("/lessonDetail/{lessonNo}")
    public String viewLesson(@PathVariable Long lessonNo, Model model, HttpSession session) {
        model.addAttribute("bannerTitle", "lesson details");

        //레슨 정보
        Lesson lesson = lessonService.getLessonById(lessonNo);

        //트레이너 정보
        TrainerResponseDTO trainerLessonResponseDTO = TrainerResponseDTO.builder()
            .trainerNo(lesson.getTrainer().getNo())
            .trainerId(lesson.getTrainer().getTrainerId())
            .trainerName(lesson.getTrainer().getTrainerName())
            .profileImg(lesson.getTrainer().getProfileImg())
            .infoTitle(lesson.getTrainer().getInfoTitle())
            .infoContent(lesson.getTrainer().getInfoContent())
            .build();

        //찜 정보
        int likeCount = likeService.likeCount(lessonNo);

        //로그인사용자가 트레이너 팔로우 했는지 확인
        Long userNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        Boolean isLike = likeService.likeCheck(userNo, lessonNo);

        LikeDto likeDto = LikeDto.builder()
            .likeCnt(likeCount)
            .likeStatus(isLike)
            .build();

        model.addAttribute("likeInfo", likeDto);
        model.addAttribute("trainerInfo", trainerLessonResponseDTO);
        model.addAttribute("lesson", lesson);

        return "detailOrCrud/lessonDetail";
    }

    //디자인용 (삭제예정)
    @GetMapping("/createComplete")
    public String createComplete(Model model) {

        //배너타이틀
        model.addAttribute("bannerTitle", "complete");
        return "detailOrCrud/createComplete";
    }

}

