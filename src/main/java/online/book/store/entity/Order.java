package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ORDER_DATE")
    private String orderDate;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "TOTAL")
    private double total;
    @Column(name = "BOOK_IMAGE_NAME")
    private String bookImageName;
    @Column(name = "PRICE")
    private Integer price;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private String getCurrentDate(){
        return LocalDate.now().toString();
    }

    public Order(String title, int quantity, double total, String bookImageName){
        this.title = title;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = getCurrentDate();
        this.bookImageName = bookImageName;
    }

}
