package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.enums.NoticeMessage;
import online.book.store.enums.Role;
import online.book.store.hash.SHA256;
import online.book.store.service.SessionService;
import online.book.store.service.SessionServiceImpl;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Table(name = "USERS")
@NoArgsConstructor
@Entity
public class User {

    private transient final SessionService sessionService = new SessionServiceImpl();
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

    @Column(name = "REGISTRATION_DATE")
    @Getter
    private String date;


    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    @Getter
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;

    @Column(name = "USER_ROLE")
    private String role;

    @Column(name = "ACCEPTED")
    @Getter
    @Setter
    private boolean accepted = false;

    @Column(name = "TOKEN")
    @Getter
    @Setter
    private String token;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    @Getter
    @Setter
    private Wishlist wishList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wait_list_id")
    private WaitList waitList;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @Setter
    @Getter
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
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

    public List<Order> getOrders() {
        if(this.orders == null) return new LinkedList<>();
        return orders;
    }


    public void addSession(UserSession userSession){
        if(this.userSessions == null) this.userSessions = new ArrayList<>();
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
        this.token = SHA256.hash(email);
        this.date = LocalDate.now().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return Objects.equals(this.id, user.id);
    }


    public void setRole(Role role){
        this.role = role.getRole();
    }

    public Role getRole(){
        return Role.toRole(this.role);
    }

    @Override
    public String toString() {
        return "User {" +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' + "";
    }

    public void setWaitList(WaitList waitList) {
        this.waitList = waitList;
        waitList.setUser(this);
    }

    public WaitList getWaitList(){
        if(this.waitList == null) {
            this.waitList = new WaitList();
            this.waitList.setUser(this);
        }
        return this.waitList;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<Notice> notices;

    public void addNotice(NoticeMessage message){
        if(this.notices == null) this.notices = new LinkedList<>();
        Notice notice = new Notice(message);
        this.notices.add(notice);
        notice.setUser(this);
    }

    public void removeNotice(Notice notice){
        if(this.notices != null){
            this.notices.remove(notice);
            notice.setUser(null);
        }
    }
}
