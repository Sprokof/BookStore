package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.enums.BookStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "BOOKS")
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "ISBN")
    @Getter
    @Setter
    private String isbn;

    @Column(name = "TITLE")
    @Getter
    @Setter
    private String title;

    @Column(name = "PUBLISHER")
    @Getter
    @Setter
    private String publisher;

    @Column(name = "PRICE")
    @Getter
    @Setter
    private int price;

    @Column(name = "YEAR_OF_PUBLISHING")
    @Getter
    @Setter
    private int yearPub;

    @Column(name = "SUBJECT")
    @Getter
    @Setter
    private String subject;

    @Column(name = "BOOK_IMAGE_NAME")
    @Getter
    @Setter
    private String bookImageName;


    @Column(name = "AVAILABLE")
    @Getter
    @Setter
    private String available;

    @Column(name = "AVAILABLE_COPIES")
    @Getter
    @Setter
    private int availableCopies;


    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    private String description;

    @Column(name = "AUTHORS")
    @Getter
    @Setter
    private String authors;

    @Column(name = "FORMAT")
    @Getter
    @Setter
    private String format;

    @Column(name = "ADDED_DATE")
    @Getter
    @Setter
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



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable (name = "BOOKS_CATEGORIES",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
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

    public List<BookReview> getBookReviews(){
        if(this.bookReviews.size() > 5){
            return this.bookReviews.subList(0, 5);
        }
        return this.bookReviews;

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return this.isbn.equals(book.isbn);
    }
}
