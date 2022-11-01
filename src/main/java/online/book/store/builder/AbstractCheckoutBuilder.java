package online.book.store.builder;

import online.book.store.entity.Checkout;

import javax.persistence.Column;

public abstract class AbstractCheckoutBuilder {

    public AbstractCheckoutBuilder builder(){
        return null;
    }

    public AbstractCheckoutBuilder firstName(String firstName){
        return null;
    }

    public AbstractCheckoutBuilder lastName(String lastName){
        return null;
    }

    public AbstractCheckoutBuilder address(String address){
        return null;
    }

    public AbstractCheckoutBuilder zip(String zip){
        return null;
    }

    public AbstractCheckoutBuilder number (String number) {return null;}

    public AbstractCheckoutBuilder exp(String exp){return null; }

    public boolean containsNull(){
        return false;
    }

    public Checkout build(){
        return null;
    }

}
