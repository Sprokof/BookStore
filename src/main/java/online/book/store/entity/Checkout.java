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
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ZIP")
    private String zip;
    @Column(name = "CARD_NUMBER")
    private String number;
    @Column(name = "EXP_DATE")
    private String exp;




    public Checkout(String firstName, String lastName, String address,
                    String zip, String cardNum, String exp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.number = cardNum;
        this.exp = exp;


    }
}
