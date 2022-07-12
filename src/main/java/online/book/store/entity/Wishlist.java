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
        int index;
        if((index = getBookIndex(book)) == - 1) return ;
            String[] booksIdArray = toArray();
            booksIdArray[index] = "";
            this.setBooksId(toString(booksIdArray));
    }

    private int getBookIndex(Book book){
        int id = book.getId();
        Integer[] tempArray = Arrays.stream(this.booksId.split("\\,")).
                map(Integer :: parseInt).
                collect(Collectors.toList()).
                toArray(Integer[] :: new);
        return Arrays.binarySearch(tempArray, id);
    }

    private String[] toArray(){
        return this.booksId.split("\\,");
    }

    private String toString(String[] array){
        StringBuilder sb = new StringBuilder();
        for(String bookId : array){
            String id = (bookId + ", ");
            sb.append(id);
        }
        return sb.toString();
    }



}
