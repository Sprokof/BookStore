package online.book.store.builder;

import online.book.store.entity.Shipment;

import java.lang.reflect.Field;

public abstract class AbstractShipmentBuilder {

    public AbstractShipmentBuilder builder(){
        return null;
    }

    public AbstractShipmentBuilder firstName(String firstName){
        return null;
    }

    public AbstractShipmentBuilder lastName(String lastName){
        return null;
    }

    public AbstractShipmentBuilder email(String email){
        return null;
    }

    public AbstractShipmentBuilder phoneNumber(String phoneNumber){
        return null;
    }

    public AbstractShipmentBuilder country(String country){
        return null;
    }

    public AbstractShipmentBuilder state(String state){
        return null;
    }

    public AbstractShipmentBuilder city(String city){
        return null;
    }

    public AbstractShipmentBuilder streetName(String city){
        return null;
    }

    public AbstractShipmentBuilder buildingNumber(String buildingNumber){
        return null;
    }

    public AbstractShipmentBuilder roomNumber(int roomNumber){
        return null;
    }

    public AbstractShipmentBuilder zipCode(String zipCode){
        return null;
    }

    public boolean containsNull(){
        return false;
    }

    public Shipment build(){
        return null;
    }

}
