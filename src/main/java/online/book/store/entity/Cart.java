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


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartItem> cartItems;


    public void addItem(CartItem item){
        if(this.cartItems ==  null) cartItems = new LinkedList<>();
        this.cartItems.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item){
        this.cartItems.remove(item);
        item.setCart(null);
    }



}
