package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "USERS")
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

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

    @Column(name = "IN_SESSION")
    @Getter
    @Setter
    private boolean inSession;


    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    @Getter
    @JoinColumn(name = "checkout_id")
    public Checkout checkout;

    @Column(name = "IS_ADMIN")
    @Getter
    @Setter
    private boolean admin = false;

    @Column(name = "REMEMBERED")
    @Getter
    @Setter
    private boolean remembered;


    @Column(name = "IP_ADDRESS")
    @Getter
    @Setter
    private String ipAddress;


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

    public User(String username, String email, String password,
                String ipAddress, boolean remembered, boolean inSession) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ipAddress = ipAddress;
        this.remembered = remembered;
        this.inSession = inSession;

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.email.equals(user.email);
    }


    public Wishlist getWishList() {
        if (this.wishList == null) this.wishList = new Wishlist();
        return wishList;
    }

    public Cart getCart() {
        if (this.cart == null) this.cart = new Cart();
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
                ", password='" + password + '\'' +
                ", ipAddress='" + ipAddress + '\'';
    }



}
