package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.status.OrderStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ORDERS")

@NoArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Getter
    @Setter
    @Column(name = "ORDER_DATE")
    private String orderDate;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "QUANTITY")
    private int quantity;

    @Getter
    @Column(name = "STATUS")
    private String status;

    @Getter
    @Setter
    @Column(name = "TOTAL")
    private double total;

    @Getter
    @Setter
    @Column(name = "BOOK_IMAGE_NAME")
    private String bookImageName;

    @Column(name = "PRICE")
    @Getter
    @Setter
    private Integer price;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
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
        this.price = (int) (total / quantity);
    }

    public void setStatus(OrderStatus status) {
        this.status = status.getText();
    }
}
