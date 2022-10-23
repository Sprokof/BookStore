package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

@Table(name = "CHECKOUTS")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "COUNTRY_STATE")
    private String state;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "CARD_NUMBER")
    private String number;
    @Column(name = "EXP_DATE")
    private String exp;
    @Column(name = "cvv")
    private String cvv;



    public Checkout(String firstName, String lastName, String state,
                    String street, String city, String zip, String cardNum, String exp, String cvv) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.number = cardNum;
        this.exp = exp;
        this.cvv = cvv;



    }
}
