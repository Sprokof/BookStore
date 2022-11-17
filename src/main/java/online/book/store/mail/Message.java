package online.book.store.mail;


import lombok.Getter;
import lombok.Setter;

public enum Message {
    CONFIRM_RESET_MESSAGE("Your code to accept new password is "),
    ACCEPT_ACCOUNT_MESSAGE("http://localhost:8080/bookstore/registration/confirm?token="),
    CONFIRM_RESET_EMAIL_MESSAGE("http://localhost:8080/bookstore/newemail/confirm?email=&token=");

    @Getter
    @Setter
    String message;

    Message (String message){
        this.message = message;
    }
}
