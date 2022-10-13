package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.service.SessionService;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Table(name = "USERS")
@NoArgsConstructor
@Entity
public class User {

    @Autowired
    private transient SessionService sessionService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "EMAIL")
    @Getter
    @Setter
    private String email;

    @Column(name = "USERNAME")
    @Getter
    @Setter
    private String username;

    @Column(name = "USER_PASSWORD")
    @Getter
    @Setter
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    @Getter
    @JoinColumn(name = "checkout_id")
    public Checkout checkout;

    @Column(name = "IS_ADMIN")
    @Getter
    @Setter
    private boolean admin = false;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishList;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @Setter
    private Cart cart;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders;

    public void addOrder(Order order) {
        if (this.orders == null) this.orders = new LinkedList<>();
        this.orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.setUser(null);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<BookReview> bookReviews;


    public void addReview(BookReview bookReview) {
        if (this.bookReviews == null) this.bookReviews = new LinkedList<>();
        this.bookReviews.add(bookReview);
        bookReview.setUser(this);
    }

    public void removeReview(BookReview bookReview) {
        this.bookReviews.remove(bookReview);
        bookReview.setUser(null);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<UserSession> userSessions;


    public void addSession(UserSession userSession){
        if(this.userSessions == null) this.userSessions = new ArrayList<>();
        userSession.setActive(true);
        this.userSessions.add(userSession);
        userSession.setUser(this);
    }


    public void removeSession(UserSession userSession){
        this.userSessions.remove(userSession);
        userSession.setUser(null);
        sessionService.deleteSession(userSession);
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.email.equals(user.email);
    }


    public Wishlist getWishList() {
        return wishList;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setWishList(Wishlist wishList) {
        this.wishList = wishList;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' + "";
    }


}
