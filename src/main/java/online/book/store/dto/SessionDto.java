package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SessionDto {
    private String userLogin;
    private boolean isUserAdmin;
    private boolean activeSession;

    public SessionDto(String userLogin, boolean isUserAdmin, boolean activeSession) {
        this.userLogin = userLogin;
        this.isUserAdmin = isUserAdmin;
        this.activeSession = activeSession;
    }
}
