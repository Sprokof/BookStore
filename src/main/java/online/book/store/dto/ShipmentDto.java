package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractShipmentBuilder;
import online.book.store.entity.Address;
import online.book.store.entity.Shipment;

import javax.persistence.Column;
import java.lang.reflect.Field;


@AllArgsConstructor
@Getter
@Setter
public class ShipmentDto extends AbstractShipmentBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String state;
    private String city;
    private String streetName;
    private String buildingNumber;
    private String roomNumber;
    private String zipCode;

    @Getter
    @Setter
    private String saveShipmenInfo = "false";


    public ShipmentDto(){

    }

    //implementation of builder


    @Override
    public AbstractShipmentBuilder builder() {
        return new ShipmentDto();
    }

    @Override
    public AbstractShipmentBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public AbstractShipmentBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public AbstractShipmentBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public AbstractShipmentBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public AbstractShipmentBuilder country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public AbstractShipmentBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public AbstractShipmentBuilder city(String city) {
        this.city = city;
        return this;
    }

    @Override
    public AbstractShipmentBuilder streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    @Override
    public AbstractShipmentBuilder buildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    @Override
    public AbstractShipmentBuilder roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    @Override
    public AbstractShipmentBuilder zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }


    @Override
    public boolean containsNull() {
        final int STATE_FIELD_INDEX = 5;
        boolean result = false;

        Field[] fields = this.getClass().getFields();

        int index = 0;
        while (index != fields.length) {

            if (fields[index] == null && index != STATE_FIELD_INDEX) {
                result = true;
                break;
            }
            index++;
        }

        return result;

    }

    @Override
    public Shipment build() {
        if(!containsNull()){
           Shipment shipment =
                   new Shipment(this.firstName, this.lastName,
                           this.email, this.phoneNumber);

           shipment.setAddress(new Address(this.country,
                    this.state, this.city,
                    this.streetName, this.buildingNumber,
                    Integer.parseInt(this.roomNumber), this.zipCode));

        return shipment;
        }
    return null;
    }

    public Shipment doShipmentBuilder(){
        return builder().
                    firstName(this.firstName).
                    lastName(this.lastName).email(this.email).
                    phoneNumber(this.phoneNumber).
                    country(this.country).
                    city(this.city).
                    streetName(this.streetName).
                    buildingNumber(this.buildingNumber).
                    roomNumber(this.roomNumber).
                    zipCode(this.zipCode).
                build();
    }
}
