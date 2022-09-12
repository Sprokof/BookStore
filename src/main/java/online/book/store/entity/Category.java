package online.book.store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "CATEGORIES")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "CATEGORY")
    private String category;

    private transient double rating;


    public Category(String category){
        this.category = category;
        this.rating = avgRating();
    }

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Book> books;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Category)) return false;

        Category cat = (Category) obj;
        return this.getCategory().equalsIgnoreCase(cat.getCategory());
    }


    private int avgRating(){
        int sumRating = 0;
        int length = this.books.size();
        for(Book book : this.books){
            //sumRating += book.bookRating;
        }
    return (sumRating / length);
    }
}
