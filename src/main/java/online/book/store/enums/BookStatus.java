package online.book.store.enums;

import lombok.Getter;

public enum BookStatus {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not available");

    @Getter
    private final String statusText;

    BookStatus(String statusText){
        this.statusText = statusText;
    }
}
