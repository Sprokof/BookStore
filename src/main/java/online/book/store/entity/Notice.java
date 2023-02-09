package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.enums.NoticeMessage;

import javax.persistence.*;


@Entity
@Table(name = "NOTICES")
@Getter
@Setter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "notice_message")
    private String message;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true ;
        if(!(obj instanceof Notice)) return false;
        Notice notice = (Notice) obj;
        return this.message.equals(notice.message);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Notice(NoticeMessage message){
        this.message = message.getMessage();
    }
}
