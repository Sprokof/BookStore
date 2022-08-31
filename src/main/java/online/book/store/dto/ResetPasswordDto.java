package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    private String password;
    private String confirmCode;
}
