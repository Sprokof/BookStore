package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.service.BookServiceImpl;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "WISHLISTS")
@NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable (name = "BOOKS_WISHLISTS",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<Book> books;



    public void addBook(Book book){
        if(this.books == null) this.books = new LinkedList<>();
        this.books.add(book);
        book.getWishlists().add(this);
    }

    public void remove(Book book){
        this.books.remove(book);
        book.getWishlists().remove(this);
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wishList", fetch = FetchType.EAGER)
    @Getter
    @Setter
    private User user;


    public boolean isEmpty(){
        return this.books == null || this.books.isEmpty();
    }

}
