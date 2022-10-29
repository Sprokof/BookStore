package online.book.store.builder;


import online.book.store.entity.Order;

public class AbstractOrderBuilder {

    public AbstractOrderBuilder build(){
        return this;
    }


    public AbstractOrderBuilder title(String title){
        return this;
    }

    public AbstractOrderBuilder quantity(int quantity){
        return this;
    }


    public AbstractOrderBuilder total(double total){
        return this;
    }

    public AbstractOrderBuilder bookImageName(String name){
        return this;
    }

    public boolean containsNull(){
        return false;
    }

    public Order buildOrder(){
        return null;
    }
}
