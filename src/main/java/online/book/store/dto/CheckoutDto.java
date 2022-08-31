package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractCheckoutBuilder;
import online.book.store.entity.Address;
import online.book.store.entity.Checkout;

import java.lang.reflect.Field;


@AllArgsConstructor
@Getter
@Setter
public class CheckoutDto extends AbstractCheckoutBuilder {

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
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    @Getter
    @Setter
    private String saveCheckout = "false";


    //implementation of builder


    @Override
    public AbstractCheckoutBuilder builder() {
        return this;
    }

    @Override
    public AbstractCheckoutBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder city(String city) {
        this.city = city;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder buildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder zipCode(String zipCode) {
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
    public Checkout build() {
        if(!containsNull()){
           Checkout shipment =
                   new Checkout(this.firstName, this.lastName,
                           this.email, this.phoneNumber);

           //shipment.setAddress(new Address(this.country,
            //        this.state, this.city,
              //      this.streetName, this.buildingNumber,
                //    Integer.parseInt(this.roomNumber), this.zipCode));

        return shipment;
        }
    return null;
    }

    public Checkout doShipmentBuilder(){
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

    public static Checkout notSaved(){
        return new Checkout(null, null, null, null);
    }
}
