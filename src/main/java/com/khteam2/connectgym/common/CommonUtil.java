package com.khteam2.connectgym.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {
    /**
     * 오늘 날짜를 반환하는 메소드
     *
     * @return yyyyMMdd 형식의 날짜 (예: {@code "20230828"})
     */
    public static String getTodayLocalDate8() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 1 ~ (9...) 난수 생성기 (최대 999,999,999)
     *
     * @param digit 1부터 9 사이의 자릿수
     * @return 파라미터로 넘긴 자릿수의 값에 따라 달라짐
     * (예: 파라미터 값으로 {@code 3}을 넘겼다면 1~999 사이의 난수를 반환함).
     * 유효하지 않은 digit을 넘길 경우 음수 반환
     */
    public static int generateRandomNumberInt(int digit) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        if (digit < 1 || digit > 9) {
            return -1;
        }

        String boundStr = randomNumberGetBoundString(digit);
        int bound = Integer.parseInt(boundStr);

        return random.nextInt(bound) + 1;
    }

    /**
     * 1 ~ (9...) 난수 생성기 (최대 999,999,999,999,999,999)
     *
     * @param digit 1부터 18 사이의 자릿수
     * @return 파라미터로 넘긴 자릿수의 값에 따라 달라짐
     * (예: 파라미터 값으로 {@code 3}을 넘겼다면 1~999 사이의 난수를 반환함).
     * 유효하지 않은 digit을 넘길 경우 음수 반환
     */
    public static long generateRandomNumberLong(int digit) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        if (digit < 1 || digit > 18) {
            return -1L;
        }

        String boundStr = randomNumberGetBoundString(digit);
        long bound = Long.parseLong(boundStr);

        return random.nextLong(bound) + 1L;
    }

    private static String randomNumberGetBoundString(int digit) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digit; i++) {
            sb.append("9");
        }

        return sb.toString();
    }

    public static Double opendataParseDouble(String str) {
        return (str == null || str.isEmpty() || str.equals("N/A")) ? null : Double.parseDouble(str);
    }

    private CommonUtil() {
    }
}
