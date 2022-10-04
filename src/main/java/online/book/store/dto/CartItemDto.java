package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {
    private String cartItemId;
    private String quantity;

    private boolean contains;

    public CartItemDto(String cartItemId, String quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    public CartItemDto(boolean contains) {
        this.contains = contains;
    }


}
