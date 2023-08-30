package com.khteam2.connectgym.common;

import com.khteam2.connectgym.member.MemberClass;

/**
 * 세션 이름을 모아두는 상수 클래스
 * <br />
 * <br />
 * <h2>사용 예시</h2>
 * 어노테이션을 통해서 꺼내올 때<br />
 * <pre>
 * &#64;GetMapping("/mypage")
 * public String myController(
 *   &#64;SessionAttribute(name = {@link SessionConstant#LOGIN_MEMBER_NO}, required = false) Long memberNo,
 *   &#64;SessionAttribute(name = {@link SessionConstant#LOGIN_MEMBER_CLASS}, required = false) {@link MemberClass} memberClass
 * ) {
 *     if (memberClass == null || memberNo == null) {
 *         log.info("로그인되어 있지 않습니다.");
 *     } else if (memberClass == {@link MemberClass#MEMBER}) {
 *         log.info("로그인되어 있는 회원은 일반 회원입니다.");
 *         Member member = this.memberRepository.findById(memberNo).orElse(null);
 *     } else if (memberClass == {@link MemberClass#TRAINER}) {
 *         log.info("로그인되어 있는 회원은 트레이너 회원입니다.");
 *         Trainer trainer = this.trainerRepository.findById(memberNo).orElse(null);
 *     }
 *
 *     return "/content/mypages";
 * }
 * </pre>
 * <br />
 * 세션에 저장할 때<br />
 * <pre>
 * &#64;GetMapping("/login")
 * public String myController(HttpSession session) {
 *     session.setAttribute({@link SessionConstant#LOGIN_MEMBER_NO}, 1L);
 *     session.setAttribute({@link SessionConstant#LOGIN_MEMBER_CLASS}, {@link MemberClass#MEMBER});
 *
 *     return "redirect:/";
 * }
 * </pre>
 */
public class SessionConstant {
    /**
     * 로그인되어 있는 회원 no
     */
    public static final String LOGIN_MEMBER_NO = "session_login_member_no";

    /**
     * 로그인되어 있는 회원 구분. 열거형 {@link MemberClass}의 값을 저장한다.
     */
    public static final String LOGIN_MEMBER_CLASS = "session_login_member_class";

    // ============================== 주문 ==============================
    public static final String ORDER_ORDER_NO = "session_order_order_no";
    public static final String ORDER_PRICE = "session_order_price";
    /**
     * 주문 시 사용되며 리스트 형태로 들어가는 강의 번호
     */
    public static final String ORDER_LESSON_LIST = "session_order_lesson_list";
}
