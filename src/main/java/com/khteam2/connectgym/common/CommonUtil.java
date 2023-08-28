package com.khteam2.connectgym.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CommonUtil {
    private static final Random random = new Random();

    /**
     * 오늘 날짜를 반환하는 메소드
     * @return yyyyMMdd 형식의 날짜 (예: {@code "20230828"})
     */
    public static String getTodayLocalDate8() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 1 ~ (9...) 난수 생성기 (최대 999,999,999)
     * @param digit 1부터 9 사이의 자릿수
     * @return 파라미터로 넘긴 자릿수의 값에 따라 달라짐
     * (예: 파라미터 값으로 {@code 3}을 넘겼다면 1~999 사이의 난수를 반환함)
     */
    public static int generateRandomNumberInt(int digit) {
        if (digit <= 0 || digit > 9) {
            return -1;
        }

        StringBuilder digitSb = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            digitSb.append("9");
        }
        int val = Integer.parseInt(digitSb.toString());

        return random.nextInt(val) + 1;
    }
}
