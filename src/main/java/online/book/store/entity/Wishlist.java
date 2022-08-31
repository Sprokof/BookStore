package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.service.BookServiceImpl;

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

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "wishlist", fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_WISHLISTS",
            joinColumns = {@JoinColumn(name = "fk_wishlist")},
            inverseJoinColumns = {@JoinColumn(name = "fk_book")})
    private List<Book> books;

    public void addBook(Book book){
        if(this.books == null) this.books = new LinkedList<>();
        this.books.add(book);
        if(book.getWishlists() == null) book.setWishlists(new LinkedList<Wishlist>());
        book.getWishlists().add(this);
    }

    public void remove(Book book){
        this.books.remove(book);
        book.getWishlists().remove(this);
    }




}
