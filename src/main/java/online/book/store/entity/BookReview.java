package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOOKS_REVIEWS")
@NoArgsConstructor
@Getter
@Setter
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "REVIEW")
    private String comment;
    @Column(name = "BOOK_RATING")
    private int bookRating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public BookReview(String comment, int bookRating){
        this.comment = comment;
        this.bookRating = bookRating;
    }
}
