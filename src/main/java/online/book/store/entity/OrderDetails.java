package online.book.store.entity;


import lombok.Getter;

import javax.persistence.*;

@Table(name = "ORDERS_DETAILS")
@Entity
public class OrderDetails {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
