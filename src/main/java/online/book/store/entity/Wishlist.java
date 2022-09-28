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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wishList")
    @Getter
    @Setter
    private List<Book> books;



    public void addBook(Book book){
        if(this.books == null) this.books = new LinkedList<>();
        this.books.add(book);
        book.setWishlist(this);
    }

    public void remove(Book book){
        this.books.remove(book);
        book.setWishlist(null);
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wishList", fetch = FetchType.EAGER)
    @Getter
    @Setter
    private User user;


    public boolean isEmpty(){
        return this.books == null || this.books.isEmpty();
    }



}
