package online.book.store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ORDERS_DETAILS")
@Entity
@NoArgsConstructor
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
    @Column(name = "DELIVERY_INTERVAL")
    private String deliveryInterval;

    @Getter
    @Setter
    @Column(name = "FIRST_DELIVERY_DATE")
    private String firstDeliveryDate;

    @Getter
    @Setter
    @Column(name = "LAST_DELIVERY_DATE")
    private String lastDeliveryDate;


    public OrderDetails(String address, String zip, String firstDeliveryDate, String lastDeliveryDate) {
        this.address = address;
        this.zip = zip;
        this.firstDeliveryDate = firstDeliveryDate;
        this.lastDeliveryDate = lastDeliveryDate;
        this.deliveryInterval = (this.firstDeliveryDate + " - " + this.lastDeliveryDate);
    }
}
