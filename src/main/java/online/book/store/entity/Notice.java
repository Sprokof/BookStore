package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.enums.NoticeMessage;
import online.book.store.enums.NoticeStatus;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "NOTICES")
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "notice_message")
    @Getter
    @Setter
    private String message;

    @Column(name = "time_stamp")
    @Getter
    private String stamp;

    @Column(name = "status")
    @Getter
    @Setter
    private String status;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true ;
        if(!(obj instanceof Notice)) return false;
        Notice notice = (Notice) obj;
        return this.message.equals(notice.message);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    public Notice(NoticeMessage message){
        this.message = message.getMessage();
        this.stamp = timestamp();
        this.status = NoticeStatus.NEW.getStatus();
    }

    private String timestamp() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        return sdf.format(now);
    }
}
