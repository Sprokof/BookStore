package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CARTS_ITEMS")
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "QUANTITY")
    @Setter
    private int quantity;

    @Column(name = "TOTAL")
    @Getter
    @Setter
    private double total;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @Getter
    @Setter
    private Cart cart;


    @OneToOne()
    @JoinColumn(name = "book_id")
    @Getter
    private Book book;


    public CartItem(){
        this.quantity = 1;
        this.total = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CartItem)) return false;
        CartItem item = (CartItem) obj;
        return this.book.getIsbn().equals(item.book.getIsbn());
    }

    @Override
    public String toString() {
        return "CartItem{ quantity = " + this.quantity + "}";
    }


    public void setBook(Book book){
        this.book = book;
        this.total = (book.getPrice() * this.quantity);
    }


}

