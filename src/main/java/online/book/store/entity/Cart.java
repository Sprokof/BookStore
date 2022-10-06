package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "CARTS")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TOTAL")
    private double total;
    @Column(name = "SUBTOTAL")
    private double subtotal;
    @Column(name = "QUANTITY")
    private int count;

    private static final double SHIPPING_PRICE = 170d;


    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "cart")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CartItem> cartItems;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    private User user;


    public Cart addItem(CartItem item){
        if(this.cartItems ==  null) cartItems = new LinkedList<>();
        this.cartItems.add(item);
        item.setCart(this);
        return this;
    }

    public void setCartItem(CartItem cartItem){
        int insertIndex;
        if((insertIndex = getIndexOfCartItem(cartItem)) == - 1) return;
        this.cartItems.set(insertIndex, cartItem);
    }

    private int getIndexOfCartItem(CartItem cartItem) {
        for (int index = 0; index < this.cartItems.size(); index++) {
            if (cartItems.get(index).equals(cartItem)) {
                return index;
            }
        }
    return - 1;
    }



    public Cart removeItem(CartItem item){
        this.cartItems.remove(item);
        item.setCart(null);
        return this;
    }


    private double calculateSubtotalPrice(){
        double subTotalSum = 0;
        for(CartItem item : this.getCartItems()){
            subTotalSum += item.getTotal();
        }
        return subTotalSum;
    }

    public void updatePrices(){
        this.subtotal = calculateSubtotalPrice();
        this.total = (subtotal + SHIPPING_PRICE);
        this.count = itemsCounts();

    }

    private int itemsCounts(){
        if(this.cartItems == null) return 0;
        return this.cartItems.size();
    }


}
