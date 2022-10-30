package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractOrderBuilder;
import online.book.store.entity.Order;
import online.book.store.entity.User;

import java.lang.reflect.Field;


@NoArgsConstructor
@Getter
@Setter
public class OrderDto extends AbstractOrderBuilder {

    private String title;
    private int quantity;
    private double total;
    private String bookImageName;
    private String sessionid;

    @Override
    public AbstractOrderBuilder build() {
        return this;
    }

    public void setBookImageName(String bookImageName) {
        this.bookImageName = bookImageName;
    }

    @Override
    public AbstractOrderBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public AbstractOrderBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }


    @Override
    public AbstractOrderBuilder total(double total) {
        this.total = total;
        return this;
    }

    @Override
    public AbstractOrderBuilder bookImageName(String name) {
        this.bookImageName = name;
        return this;
    }

    @Override
    public boolean containsNull() {
        Field[] fields = this.getClass().getFields();
        int index  = 0;
        while(index != fields.length){
            if(fields[index] == null) return true;
            index ++ ;
        }
    return false;
    }

    @Override
    public Order buildOrder() {
        Order order = null;
        if(!containsNull()){
            order = new Order(this.title, this.quantity,
                    this.total, this.bookImageName);
        }
        return order;
    }

    public Order doOrderBuilder(){
        return build().title(this.title).
                quantity(this.quantity).
                total(this.total).
                bookImageName(this.bookImageName).
                buildOrder();
    }
}

