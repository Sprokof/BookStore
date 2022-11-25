package online.book.store.enums;

import lombok.Getter;

public enum OrderStatus {
    PAID("Paid"),
    IN_DELIVERY("In delivery"),
    DELIVERED("Delivered"),
    RECEIVED("Received");

    @Getter
    String text;

    OrderStatus(String text) {
        this.text = text;
    }
}
