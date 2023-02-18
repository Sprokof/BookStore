package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private boolean active;
    private String sessionid;
    private boolean adminSession;

    public SessionDto(boolean active, boolean adminSession) {
        this.active = active;
        this.adminSession = adminSession;
    }

    public SessionDto(boolean active) {
        this.active = active;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public boolean isAdminSession() {
        return adminSession;
    }

    public void setAdminSession(boolean adminSession) {
        this.adminSession = adminSession;
    }
}
