package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "CART")
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
    @Column(name = "COUNT")
    private int count;

    private static final double SHIPPING_PRICE = 17d;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartItem> cartItems;


    public Cart addItem(CartItem item){
        if(this.cartItems ==  null) cartItems = new LinkedList<>();
        this.cartItems.add(item);
        item.setCart(this);
        return this;
    }

    public void removeItem(CartItem item){
        this.cartItems.remove(item);
        item.setCart(null);
    }

    private double calculateTotalPrice(){
        double totalSum = 0;
        for(CartItem item : this.getCartItems()){
            totalSum += item.getTotal();
        }
        return totalSum + SHIPPING_PRICE;
    }

    private double calculateSubTotalPrice(){
        double subTotalSum = 0;
        for(CartItem item : this.getCartItems()){
            subTotalSum += item.getTotal();
        }
        return subTotalSum;
    }

    public void updatePrices(){
        this.subtotal = calculateSubTotalPrice();
        this.total = calculateTotalPrice();
        this.count = this.cartItems.size();
    }




}
