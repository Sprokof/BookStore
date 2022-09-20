package online.book.store.mail;


public enum Subject {
    RESET_PASSWORD("Code To Reset Password");

    String subjectText;

    Subject(String subjectText){
        this.subjectText = subjectText;
    }
    String content(){
        return this.subjectText;
    }




}
