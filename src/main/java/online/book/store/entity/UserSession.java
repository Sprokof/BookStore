package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

    @Column(name = "DATESTAMP")
    @Getter
    @Setter
    private String dateStamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stat_id")
    @Getter
    @Setter
    private SessionStatistics sessionStatistics;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof UserSession)) return false;
        UserSession userSession = (UserSession) obj;
        return this.sessionId.equals(userSession.sessionId);
    }

    public UserSession(String sessionId, SessionStatistics statistics) {
        this.sessionId = sessionId;
        this.dateStamp = datestamp();
        this.sessionStatistics = statistics;
    }

    @Override
    public String toString() {
        return "UserSession { " +
                ", sessionId='" + sessionId + '\'' +
                ", user=" + user + " " +
                '}';
    }

    private String datestamp(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        return sdf.format(now);
    }

}
