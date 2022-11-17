package online.book.store.mail;


public enum Subject {
    RESET_PASSWORD("Code To Reset Password"),
    CONFIRM_REGISTRATION("Confirm Registration"),
    CONFIRM_NEW_EMAIL("Confirm New Email");

    String subjectText;

    Subject(String subjectText){
        this.subjectText = subjectText;
    }
    String content(){
        return this.subjectText;
    }




}
