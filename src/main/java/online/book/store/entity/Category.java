package online.book.store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @Column(name = "CATEGORY_RATING")
    private Double rating = 0d;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> books;

    public Category(String category){
        this.category = category;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Category)) return false;

        Category cat = (Category) obj;
        return this.category.equalsIgnoreCase(cat.getCategory());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category=" + category;
    }
}
