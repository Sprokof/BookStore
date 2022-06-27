package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "STATE")
    private String state;
    @Column(name = "City")
    private String city;
    @Column(name = "STREET_NAME")
    private String streetName;
    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;
    @Column(name = "ROOM_NUMBER")
    private int roomNumber;
    @Column(name = "ZIP_CODE")
    private String zipCode;

    public Address(String country, String state, String city,
                   String streetName, String buildingNumber, int roomNumber, String zipCode){
        this.country = country;
        this.state = state;
        this.city = city;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.roomNumber = roomNumber;
        this.zipCode = zipCode;
    }

}
