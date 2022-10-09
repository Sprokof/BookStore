package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private boolean itemContains;
    private int itemsQuantity;

    public CartDto(boolean itemContains) {
        this.itemContains = itemContains;
    }

    public CartDto(int itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }
}
