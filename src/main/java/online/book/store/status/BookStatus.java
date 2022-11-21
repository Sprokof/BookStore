package online.book.store.status;

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
