package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.status.BookStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
    private int price;
    @Column(name = "YEAR_OF_PUBLISHING")
    private int yearPub;
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



    @Getter
    @Setter
    @Column(name = "AVG_RATING")
    private double bookRating = 0d;


    public Book(String isbn, String title, String publisher,
                int price, int yearPub,
                String subject, String bookImageName, BookStatus status, int availableCopies,
                String description, String authors, String format){
        this.isbn = isbn;
        this.title = titleToUpperCase(title);
        this.publisher = publisher;
        this.price = price;
        this.yearPub = yearPub;
        this.subject = subject;
        this.bookImageName = bookImageName;
        this.available = status.getStatusText();
        this.availableCopies = availableCopies;
        this.description = description;
        this.authors = authors;
        this.format = format;
        this.addedDate = currentDate();

    }

    @OneToMany(mappedBy = "book")
    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CartItem> cartItems;



    @ManyToMany()
    @JoinTable (name = "BOOKS_CATEGORIES",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories;


    public void addCategory(Category category){
        if(this.categories == null) this.categories = new LinkedList<>();
        this.categories.add(category);
        category.getBooks().add(this);

    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBooks().remove(this);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @LazyCollection(LazyCollectionOption.FALSE)
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

    @Getter
    @Setter
    @ManyToMany(mappedBy = "books")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Wishlist> wishlists;


    @Override
    public String toString(){
        return "book { isbn" + this.isbn + "}";
    }

    private LocalDate currentDate(){
        return LocalDate.now();
    }


    private String titleToUpperCase(String title){
        String upperCaseLetter = (String.valueOf(title.charAt(0)).toUpperCase());
        return String.format("%s%s", upperCaseLetter, title.substring(1));
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return this.isbn.equals(book.isbn);
    }
}
