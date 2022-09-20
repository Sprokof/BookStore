package online.book.store.mail;

public enum Message {
    EMPTY_MESSAGE("Empty message"),
    CONFIRM_MESSAGE("Your code to accept new password is ");

    String message;

    Message (String message){
        this.message = message;
    }
}
