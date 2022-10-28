package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USERS_SESSIONS")
@NoArgsConstructor
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "SESSION_ID")
    @Getter
    @Setter
    private String sessionId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof UserSession)) return false;
        UserSession userSession = (UserSession) obj;
        return this.sessionId.equals(userSession.sessionId);
    }

    public UserSession(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                ", sessionId='" + sessionId + '\'' +
                ", user=" + user +
                '}';
    }
}
