package online.book.store.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setHost(host);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setPort(port);

        Properties properties = sender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return sender;
    }

}
