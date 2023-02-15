package online.book.store.enums;

import lombok.Getter;

public enum NoticeStatus {
    NEW("New"),
    OLD("Old"),
    READ("Read");

    @Getter
    private final String status;

    NoticeStatus(String status){
        this.status = status;
    }
}
