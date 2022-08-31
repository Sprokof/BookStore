package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.dto.CheckoutDto;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "users")

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

    @Column(name = "IP_ADDRESS")
    @Getter
    @Setter
    private String ipAddress;

    @Column(name = "REMEMBERED")
    @Getter
    @Setter
    private boolean remembered;

    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    @JoinColumn(name = "checkout_id")
    public Checkout checkout = CheckoutDto.notSaved();


    @Value("admin.email")
    transient String adminEmail;
    transient boolean admin;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishList;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @Getter
    @Setter
    private Cart cart;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @Getter
    private List<Order> orders;

    public void addOrder(Order order){
        if(this.orders == null) this.orders = new LinkedList<>();
        this.orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order){
        this.orders.remove(order);
        order.setUser(null);
    }

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

    public User(String email, String username, String password, String ipAddress, boolean remembered){
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin();
        this.ipAddress = ipAddress;
        this.remembered = remembered;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.email.equals(user.email);
    }


    private boolean admin(){
        return this.email.equals(adminEmail);
    }

    public Wishlist getWishList() {
        if(this.wishList == null) this.wishList = new Wishlist();
        return wishList;
    }

    public void setWishList(Wishlist wishList) {
        this.wishList = wishList;
    }
}
