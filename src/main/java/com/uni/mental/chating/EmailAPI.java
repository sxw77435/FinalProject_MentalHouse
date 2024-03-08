package com.uni.mental.chating;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailAPI {

    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        // 보낸사람의 메일 주소
        String senderEmail = "mentalhouse0129@gmail.com";
        // 앱 비밀 번호
        String password = "ujht zdxm dvfi mcov";

        // 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); 
        props.put("mail.smtp.starttls.enable", "true");

        // 대화 실례를 창립하기
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            
            Message message = new MimeMessage(session);
            // 보낸사람의 메일 설정
            message.setFrom(new InternetAddress(senderEmail));
            // 메일 받는사람 설정
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            // 메일 콘텐츠 설정
            message.setSubject(subject);
            // 메일 문장 설정
            message.setContent(body, "text/html; charset=utf-8");

            // 메일 보내기
            Transport.send(message);
            System.out.println("메일 성공적으로 발송 했습니다！");
        } catch (MessagingException e) {
            System.out.println("메일 보내 실패했습니다！");
            e.printStackTrace();
            throw e;
        }
    }

//    public static void main(String[] args) {
//        String recipientEmail = "sxw77435@gmail.com"; // 메일 주소
//        String subject = "Mental house"; // 메일 타이틀
//        String body = "This is a test email sent from JavaMail API."; //메일 내용
//
//        try {
//            EmailAPI.sendEmail(recipientEmail, subject, body);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }


}
