package online.book.store.enums;

public enum NoticeMessage {
    NOW_AVAILABLE(" is now available!");

    private String message;

    NoticeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }


    public NoticeMessage setBookTitle(String title){
        this.message = (title += message);
        return this;
    }

    public void unsetBookTitle(){
        this.message = " is now available";
    }
}
