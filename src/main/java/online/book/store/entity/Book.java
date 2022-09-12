package online.book.store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.service.BookService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
    @Column(name = "YEAR_OF_PUBLISHING")
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



    transient double bookRating;


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

    @ManyToMany(cascade = CascadeType.ALL)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
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

    @ManyToMany(mappedBy = "books",
            cascade = CascadeType.ALL)
    private List<Wishlist> wishlists;



    @Override
    public String toString(){
        return String.format("%s%s%s%s%s%s%s",
                        this.isbn.toLowerCase(Locale.ROOT),
                        this.title.toLowerCase(Locale.ROOT).replaceAll("\\s", ""),
                        this.publisher.toLowerCase(Locale.ROOT),
                        this.yearPub.toLowerCase(Locale.ROOT), this.subject,
                        this.description.toLowerCase(Locale.ROOT), this.price);
    }

    private LocalDate currentDate(){
        return LocalDate.now();
    }

}
