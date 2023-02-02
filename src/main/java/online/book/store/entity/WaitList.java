package online.book.store.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "WAITS_LISTS")
public class WaitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private int userId;

    @ManyToMany()
    @JoinTable (name = "BOOKS_WAITS_LISTS",
            joinColumns = @JoinColumn(name = "wait_list_id"),
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<Book> books;


    public void addBook(Book book){
        if(this.books == null) this.books = new LinkedList<>();
        this.books.add(book);
        book.getWaitLists().add(this);
    }

    public void removeBook(Book book){
        if(this.books == null) return ;
        this.books.remove(book);
        book.getWaitLists().remove(this);
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "waitList", fetch = FetchType.EAGER)
    @Getter
    private User user;


    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }
}
