package online.book.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.service.BookServiceImpl;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Column(name = "BOOKS_ID")
    @Getter
    @Setter
    private String booksId;

    public List<Book> getBooksFromWishlist(){
        BookServiceImpl bookService = new BookServiceImpl();
        String[] booksId = this.booksId.split("\\,");
        return Arrays.stream(booksId).
                map((id) -> bookService.
                            getBookById(Integer.parseInt(id))).
                            collect(Collectors.toList());
    }


    public void removeBook(Book book){
        String removesBookId = (String.valueOf(book.getId()));
        String[] idArray = this.booksId.split("\\,");

        int index = 0;
        while(index != idArray.length){
            if(idArray[index].equals(removesBookId)){
                idArray[index] = "";
            }
            index ++ ;
        }

    }



}
