package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BOOKS_REVIEWS")
@NoArgsConstructor

public class BookReview {
    public static final double MIN_POPULAR_RATING = 3.0d, MAX_POPULAR_RATING = 5.0d;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Getter
    @Setter
    private transient String author;

    @Column(name = "REVIEW")
    @Getter
    @Setter
    private String review;

    @Column(name = "BOOK_RATING")
    @Setter
    @Getter
    private double score;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    @Getter
    @Setter
    private Book book;

    @Column(name = "POST_DATE")
    @Getter
    private String postDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Getter
    private User user;


    public BookReview(String review, int score){
        this.review = review;
        this.score = score;
        this.postDate = LocalDate.now().toString();
    }

    public void setUser(User user){
        this.author = user.getUsername();
        this.user = user;
    }

}
