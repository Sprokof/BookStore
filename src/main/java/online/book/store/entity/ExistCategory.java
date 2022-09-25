package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "EXIST_CATEGORIES")
public class ExistCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Column(name = "CATEGORY")
    @Getter
    @Setter
    private String category;

    public ExistCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ExistCategory{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
