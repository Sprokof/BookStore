package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String userLogin;
    private boolean isUserAdmin;
    private boolean activeSession;
    private boolean itemContains;
    private int quantity;

}
