package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "BOOKS")
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PUBLISHER")
    private String publisher;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "YEAR_OF_PUBLISHER")
    private String yearPub;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "BOOK_IMAGE_NAME")
    private String bookImageName;
    @Column(name = "AVAILABLE")
    private String available;
    @Column(name = "AVAILABLE_COPIES")
    private int availableCopies;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "AUTHORS")
    private String authors;
    @Column(name = "FORMAT")
    private String format;
    @Column(name = "ADDED_DATE")
    private LocalDate addedDate;



    transient int bookRating = getAvgRating();


    public Book(String isbn, String title, String publisher,
                                double price, String yearPub,
                                String subject, String bookImageName, String available, int availableCopies,
                                String description, String authors, String format){
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.price = price;
        this.yearPub = yearPub;
        this.subject = subject;
        this.bookImageName = bookImageName;
        this.available = available;
        this.availableCopies = availableCopies;
        this.description = description;
        this.authors = authors;
        this.format = format;
        this.addedDate = currentDate();

    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.EAGER)
    @JoinTable(name = "BOOKS_CATEGORIES",
            joinColumns = {@JoinColumn(name = "fk_book")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category")})
    private List<Category> categories;


    public void addCategory(Category category){
        if(this.categories == null) this.categories = new LinkedList<>();
        this.categories.add(category);
        if(category.getBooks() == null) category.setBooks(new LinkedList<Book>());
        category.getBooks().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBooks().remove(this);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.EAGER)
    private List<BookReview> bookReviews;


    public void addReview(BookReview bookReview){
        if(this.bookReviews == null) this.bookReviews = new LinkedList<>();
        this.bookReviews.add(bookReview);
        bookReview.setBook(this);
    }

    public void removeBookReview(BookReview bookReview){
        this.bookReviews.remove(bookReview);
        bookReview.setBook(null);
    }


    public int getAvgRating() {
        if(this.bookReviews == null) return 0;
        Integer[] scores = this.bookReviews.
                stream().map(BookReview::getBookRating).
                collect(Collectors.toList()).toArray(Integer[]::new);

        int scoreSum = 0, length = scores.length, index = 0;

        while (index != length) {
            scoreSum += scores[index++];
        }
        return (scoreSum / length);
    }

    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s",
                        this.isbn, this.title, this.publisher,
                        this.yearPub, this.subject, this.description);
    }

    private LocalDate currentDate(){
        return LocalDate.now();
    }
}
