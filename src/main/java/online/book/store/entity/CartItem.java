package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CARTS_ITEMS")
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "TOTAL")
    private double total;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;


    public CartItem(String isbn, double price, int quantity){
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
        this.total = (this.quantity * this.price);

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CartItem)) return false;

        CartItem item = (CartItem) obj;
        return this.isbn.equals(item.isbn);
    }
}
