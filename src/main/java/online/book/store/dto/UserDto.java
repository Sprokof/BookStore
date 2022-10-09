package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String login;
    private boolean inSession;
    private boolean isAdmin;
    private boolean remember;

    public UserDto(String login, boolean inSession, boolean isAdmin) {
        this.login = login;
        this.inSession = inSession;
        this.isAdmin = isAdmin;
    }
}
