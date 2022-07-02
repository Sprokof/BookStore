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
    @Column(name = "PATH_TO_IMAGE")
    private String path;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String getCurrentDate(){
        return LocalDate.now().toString();
    }

    public Order(String title, int quantity, double total, String path){
        this.title = title;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = getCurrentDate();
        this.path = path;
    }

}
