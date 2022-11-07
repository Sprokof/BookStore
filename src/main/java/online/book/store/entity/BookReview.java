package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOOKS_REVIEWS")
@NoArgsConstructor

public class BookReview {
    public static final double MIN_POPULAR_RATING = 3.0d, MAX_POPULAR_RATING = 5.0d;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;


    public BookReview(String review, int score){
        this.review = review;
        this.score = score;
    }

}
