package com.food.order.utils;

//import com.google.appengine.api.mail.MailService;
//import com.google.appengine.api.mail.MailServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class mailUtils {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

 //   private String code;
 //   private static ExecutorService executorService= Executors.newFixedThreadPool(2);
    public void sendMail(String code) {
        try {
            MimeMessage mimeMessage=null;
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMailMessage mimeMessageHelper=new MimeMailMessage(mimeMessage);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(code);
            mimeMessageHelper.setSubject("verification code");
            mimeMessageHelper.setTo("a972500794@gmail.com");
            javaMailSender.send(mimeMessage);
            log.info("successfully sending the mail");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
//        Runnable emailTask=()->{
//    public mailUtils(String code){
//        this.code=code;
//    }

//    @Override
//    public void run() {
//        Properties props=System.getProperties();
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.host","smtp.qq.com");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.user","972500794@qq.com");
//        props.put("mail.password","jglbqlbmfidjbffd");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("972500794@qq.com", "jglbqlbmfidjbffd");
//            }
//        });
//        session.setDebug(true);
//
//        try {
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress("972500794@qq.com"));
//            msg.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress("a972500794@gmail"));
//            msg.setSubject("verification code");
//            msg.setText(code);
//
//        } catch (AddressException e) {
//            // ...
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            // ...
//            e.printStackTrace();
//        }
//    }
//        executorService.submit(emailTask);
//        executorService.shutdown();
//    }

//    public static void sendingMail(String code){
//        HtmlEmail email=new HtmlEmail();
//        email.setCharset("UTF-8");
//        email.setHostName("smtp.gmail.com");
//        try {
//            email.addTo("972500794@qq.com");
//            email.setFrom("a972500794@gmail.com");
//            email.setAuthentication("a972500794@gmail.com",);
//            email.setSubject("verification code");
//            email.setMsg(code);
//            email.send();
//        } catch (EmailException e) {
//            log.info(e.getMessage());
//        }
//    }


//    public static void sendEmail(String code){
//        // 创建邮件消息
//
//        MailService mailService = MailServiceFactory.getMailService();
//        Message message = new Message();
//        message.setFrom("dilian_1995@abv.bg");
//        message.setTo("a972500794@gmail.com") // 收件人
//                .withTextBody(code) // 邮件正文
//                .build();
//
//        // 发送邮件
//        mailService.send(message);
//    }
