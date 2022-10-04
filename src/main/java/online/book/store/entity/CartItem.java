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
    @Column(name = "TITLE")
    private String title;
    @Column(name ="IMAGE_NAME")
    private String imageName;
    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "TOTAL")
    private double total;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    public CartItem(String title, String imageName, String isbn, double price){
        this.title = title;
        this.imageName = imageName;
        this.isbn = isbn;
        this.price = price;
        this.quantity = 1;
        this.total = (this.quantity * this.price);

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CartItem)) return false;

        CartItem item = (CartItem) obj;
        return this.isbn.equals(item.isbn);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                ", title='" + title + '\'' +
                ", imageName='" + imageName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                ", cart=" + cart +
                '}';
    }
}
