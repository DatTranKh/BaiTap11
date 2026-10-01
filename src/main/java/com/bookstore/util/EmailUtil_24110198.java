package com.bookstore.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailUtil_24110198 {
    // Thay đổi thông tin email gửi tại đây
    private static final String SENDER_EMAIL = "datgamer206@gmail.com";
    private static final String APP_PASSWORD = "wyinkbexkwylidas";

    public static String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public static boolean sendOtpEmail(String recipientEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Bookstore System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Mã xác thực đăng ký tài khoản Bookstore");
            message.setText("Xin chào,\n\nMã OTP kích hoạt tài khoản của bạn là: " + otp 
                    + "\nMã xác thực có hiệu lực trong vòng 5 phút. Vui lòng không chia sẻ mã này cho bất kỳ ai.");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}