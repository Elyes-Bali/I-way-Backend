package com.example.project.backend.services.mailing;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendDoctorVerificationEmail(String toEmail, String doctorName) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("✅ Your Account Has Been Verified");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: #ffffff; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #27ae60;">🎉 Account Verified Successfully!</h2>
                        <p style="font-size: 16px; color: #333;">Hello <strong>Dr. %s</strong>,</p>
                        <p style="font-size: 16px; color: #333;">
                            We’re excited to let you know that your account has been <strong>successfully verified</strong> by our admin team.
                        </p>
                        <p style="font-size: 16px; color: #333;">
                            You can now <a href="http://localhost:4200/login" style="color: #2980b9;">log in</a> and begin using HealLink to manage appointments, connect with patients, and more!
                        </p>
                        <p style="font-size: 16px; color: #555;">Best regards,<br>The <strong>HealLink</strong> Team</p>
                    </div>
                </body>
                </html>
            """.formatted(doctorName);

            helper.setText(htmlContent, true); // Enable HTML content
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }


    public void sendAppointmentStatusEmail(String toEmail, String patientName,String doctorName ,String status) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("📅 Appointment Status Updated");

            String htmlContent = """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
                <div style="background-color: #ffffff; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
                    <h2 style="color: #2980b9;">Appointment Update</h2>
                    <p style="font-size: 16px; color: #333;">Hello <strong>%s</strong>,</p>
                    <p style="font-size: 16px; color: #333;">
                        We wanted to let you know that the status of your appointment with <strong>%s</strong> has been updated to: 
                        <strong style="color: #27ae60;">%s</strong>.
                    </p>
                    <p style="font-size: 16px; color: #555;">Please log in to your account to view details.</p>
                    <p style="font-size: 16px; color: #555;">Warm regards,<br>The <strong>HealLink</strong> Team</p>
                </div>
            </body>
            </html>
        """.formatted(patientName,doctorName ,status);

            helper.setText(htmlContent, true); // HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send appointment status email", e);
        }
    }


    public void sendPharmacyVerificationEmail(String toEmail, String doctorName) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("✅ Your Account Has Been Verified");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: #ffffff; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #27ae60;">🎉 Account Verified Successfully!</h2>
                        <p style="font-size: 16px; color: #333;">Hello <strong>Dear. %s</strong>,</p>
                        <p style="font-size: 16px; color: #333;">
                            We’re excited to let you know that your account has been <strong>successfully verified</strong> by our admin team.
                        </p>
                        <p style="font-size: 16px; color: #333;">
                            You can now <a href="http://localhost:4200/login" style="color: #2980b9;">log in</a> and begin using HealLink to manage prescriptions, connect with patients, and more!
                        </p>
                        <p style="font-size: 16px; color: #555;">Best regards,<br>The <strong>HealLink</strong> Team</p>
                    </div>
                </body>
                </html>
            """.formatted(doctorName);

            helper.setText(htmlContent, true); // Enable HTML content
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }


}
