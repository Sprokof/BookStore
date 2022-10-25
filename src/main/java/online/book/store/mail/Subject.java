package online.book.store.mail;


public enum Subject {
    RESET_PASSWORD("Code To Reset Password"),
    CONFIRM_REGISTRATION("Confirm Registration");

    String subjectText;

    Subject(String subjectText){
        this.subjectText = subjectText;
    }
    String content(){
        return this.subjectText;
    }




}
