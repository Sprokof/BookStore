package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    transient boolean admin;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private WishList wishList;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Shipment shipment;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<BookReview> bookReviews;


    public void addReview(BookReview bookReview){
        if(this.bookReviews == null) this.bookReviews = new LinkedList<>();
        this.bookReviews.add(bookReview);
        bookReview.setUser(this);
    }

    public void removeReview(BookReview bookReview){
        this.bookReviews.remove(bookReview);
        bookReview.setUser(null);
    }



    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.shipment = null;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.password.equals(user.password) && this.email.equals(user.email);
    }

}
