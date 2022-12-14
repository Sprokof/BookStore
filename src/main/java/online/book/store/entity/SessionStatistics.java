package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "SESSIONS_STATISTICS")
@Entity
@NoArgsConstructor
public class SessionStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "ACTIVE_SESSIONS_COUNT")
    @Getter
    @Setter
    private long activeSessionCount;

    @Column(name = "ACTIVE_USERS_COUNT")
    @Getter
    @Setter
    private long usersCount;


}
