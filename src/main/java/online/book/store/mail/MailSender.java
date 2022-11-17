package online.book.store.mail;


import online.book.store.service.SignService;
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


    public void send(String emailTo, Subject subject, SignService service) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject.content());
        mailMessage.setText(generateMessage(subject, service, emailTo));

        execute(mailMessage);

    }

    private String generateMessage(Subject subject, SignService signService, String email) {
        if (subject.equals(Subject.RESET_PASSWORD)) {
            return Message.CONFIRM_RESET_MESSAGE.message + signService.getConfirmationCode();
        }
        else if(subject.equals(Subject.CONFIRM_REGISTRATION)) {
            String link = Message.ACCEPT_ACCOUNT_MESSAGE.message;
            return (link + (signService.generateToken(email)));
        }
        else {
            String link = Message.CONFIRM_RESET_EMAIL_MESSAGE.message;
            String leftLinkPart = (link.substring(0, (link.indexOf("=") + 1)) + email);
            String rightLinkPart = ((link.substring(link.indexOf("&"))) + signService.generateToken(email));
            return (leftLinkPart + rightLinkPart);
        }
    }


    private void execute(SimpleMailMessage mailMessage){
        new Thread(() -> this.mailSender.send(mailMessage)).start();
    }
}