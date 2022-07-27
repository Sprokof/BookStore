package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "BOOKS_CATEGORIES")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "CATEGORY_RATING")
    private int rating;


    public Category(String category){
        this.category = category;
        this.rating = avgRating();
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private List<Book> books;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Category)) return false;

        Category cat = (Category) obj;
        return this.getCategory().equals(cat.getCategory());
    }


    private int avgRating(){
        int sumRating = 0;
        int length = this.books.size();
        for(Book book : this.books){
            sumRating += book.bookRating;
        }
    return (sumRating / length);
    }
}
