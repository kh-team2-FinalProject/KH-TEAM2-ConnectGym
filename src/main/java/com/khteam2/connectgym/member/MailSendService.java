package com.khteam2.connectgym.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailSendService {
    @Autowired
    private JavaMailSender mailSender;
    private int authNumber;

    // 랜덤 인증코드 생성
    public void makeRandomNumber() {
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkNum);
        authNumber = checkNum;
    }

    // 이메일 보낼 양식
    public String joinEmail(String email) {
        makeRandomNumber();
        String setFrom = "acou99";
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = "홈페이지를 방문해주셔서 감사합니다." +
            "<br><br>" + "인증번호는 " + authNumber + "입니다." +
            "<br>" +
            "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    // 이메일 전송 메서드
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
