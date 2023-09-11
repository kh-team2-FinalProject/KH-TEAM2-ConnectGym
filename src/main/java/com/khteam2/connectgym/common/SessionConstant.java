package com.khteam2.connectgym.common;

import com.khteam2.connectgym.member.MemberClass;

/**
 * 세션 이름을 모아두는 상수 클래스
 * <br />
 * <br />
 * <h2>사용 예시</h2>
 * SessionAttribute 어노테이션을 통해서 값을 꺼내올 때<br />
 *
 * <pre>
 * GetMapping("/mypage")
 * public String myController(
 *   SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
 *   SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass
 * ) {
 *     if (loginMemberClass == null || loginMemberNo == null) {
 *         log.info("로그인되어 있지 않음");
 *     } else if (loginMemberClass == MemberClass.MEMBER) {
 *         log.info("일반 회원 로그인됨");
 *         Member member = this.memberRepository.findById(loginMemberNo).orElse(null);
 *     } else if (loginMemberClass == MemberClass.TRAINER) {
 *         log.info("트레이너 회원 로그인됨");
 *         Trainer trainer = this.trainerRepository.findById(loginMemberNo).orElse(null);
 *     }
 *
 *     return "/content/mypages";
 * }
 * </pre>
 * <p>
 * <br />
 * HttpSession을 이용해서 세션에 값을 저장할 때<br />
 *
 * <pre>
 * GetMapping("/login")
 * public String myController(HttpSession session) {
 *     session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, 1L);
 *     session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.MEMBER);
 *
 *     return "redirect:/";
 * }
 * </pre>
 *
 * @see SessionConstant#LOGIN_MEMBER_NO
 * @see SessionConstant#LOGIN_MEMBER_CLASS
 * @see MemberClass
 */
public class SessionConstant {
    /**
     * 로그인되어 있는 회원 번호 (멤버 구분과 상관없이 저장)
     */
    public static final String LOGIN_MEMBER_NO = "session_login_member_no";

    /**
     * 로그인되어 있는 회원 구분. 일반 회원인지, 아니면 트레이너 회원인지 구분하는 세션 변수이며
     * 열거형 클래스 {@link MemberClass}의 값을 저장한다.
     */
    public static final String LOGIN_MEMBER_CLASS = "session_login_member_class";

    // ============================== 주문 ==============================
    public static final String ORDER_ORDER_NO = "session_order_order_no";
    public static final String ORDER_PRICE = "session_order_price";
    /**
     * 주문 시 사용되며 리스트 형태로 들어가는 강의 번호
     */
    public static final String ORDER_LESSON_LIST = "session_order_lesson_list";

    private SessionConstant() {
    }
}
