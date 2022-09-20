package online.book.store.mail;

import online.book.store.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;


    public void send(String emailTo, Subject subject, SignInService service) {
        sleep();
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject.content());
        mailMessage.setText(generateMessage(subject, service.getConfirmationCode()));

        mailSender.send(mailMessage);

    }


    private String generateMessage(Subject subject, String code) {
        String message = Message.EMPTY_MESSAGE.message;
        if (subject.equals(Subject.RESET_PASSWORD)) {
            message = (Message.CONFIRM_MESSAGE.message + code);
        }
        return message;
    }


    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.getCause();
        }
    }
}