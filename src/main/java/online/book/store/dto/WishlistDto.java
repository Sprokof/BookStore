package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private boolean itemContains;
    private String isbn;
    private String sessionid;

    public WishlistDto(boolean itemContains) {
        this.itemContains = itemContains;
    }

    public WishlistDto(String isbn, String sessionid) {
        this.isbn = isbn;
        this.sessionid = sessionid;
    }
}
