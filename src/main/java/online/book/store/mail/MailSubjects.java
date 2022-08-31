package online.book.store.mail;

public enum MailSubjects {
    RESET_PASSWORD("Code to reset password");

    String subject;

    MailSubjects(String subject){
        this.subject = subject;
    }
    String content(){
        return this.subject;
    }
}
