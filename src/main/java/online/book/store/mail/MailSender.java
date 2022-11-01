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
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject.content());
        mailMessage.setText(generateMessage(subject, service, emailTo));

        execute(mailMessage);

    }

    private String generateMessage(Subject subject, SignInService signInService, String email) {
        if (subject.equals(Subject.RESET_PASSWORD)) {
            return Message.CONFIRM_RESET_MESSAGE.message + signInService.getConfirmationCode();
        } else {
            String link = Message.ACCEPT_ACCOUNT_MESSAGE.message;

            return (link + (signInService.generateToken(email)));
        }
    }


    private void execute(SimpleMailMessage mailMessage){
        new Thread(() -> this.mailSender.send(mailMessage)).start();
    }
}