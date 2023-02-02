package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitListDto {
    private String sessionid;
    private String isbn;
    private boolean itemContains;

    public WaitListDto(boolean itemContains) {
        this.itemContains = itemContains;
    }
}
