package online.book.store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ORDERS_DETAILS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "ADDRESS")
    private String address;

    @Getter
    @Setter
    @Column(name = "ZIP")
    private String zip;

    @Getter
    @Setter
    @Column(name = "DELIVERY_DATES_INTERVAL")
    private String deliveryDatesInterval;



}
