package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private boolean active;
    private String sessionid;
    private boolean adminSession;

    public SessionDto(String sessionid) {
        this.sessionid = sessionid;
    }

    public SessionDto(boolean active) {
        this.active = active;
    }
}
