package online.book.store.engines;

import online.book.store.entity.Book;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class SortEngine{
    private static SortEngine instance;

    public static SortEngine instanceSortEngine(){
        if(instance == null){
            instance = new SortEngine();
        }
        return instance;
    }

    private SortEngine(){}


    private List<Book> bookList;

    private SortConfig sortConfig;

    public List<Book> getSortBooks() {
        if(sortConfig == null) return null;

        switch (sortConfig.getSelectedType().name()) {
            case "Popularity":
                this.bookList.sort(Comparator.comparingInt(Book::getBookRating));
                break;
            case "Low to High":
                this.bookList.sort(Comparator.comparingDouble(Book::getPrice));
                break;
            case "High to Low":
                this.bookList.sort((book, bookNext) -> {
                    return ((int) bookNext.getPrice() - (int) book.getPrice());
                });
                break;
            case "Latest":
                this.bookList.sort((book, bookNext) -> compareAddedDate(book.getAddedDate(), bookNext.getAddedDate()));
                break;
            default:
                this.bookList.sort(Comparator.comparingInt(Book::getBookRating));
                break;

        }
        return this.bookList;
    }

    public SortEngine setBookListToSort(List<Book> bookList){
        this.bookList = bookList;
        return this;
    }

    public void setSortConfig(SortConfig sortConfig){
        this.sortConfig = sortConfig;
    }

    public SortConfig getSortConfig(){
        if(this.sortConfig == null) return new SortConfig();
        return this.sortConfig;
    }

    private int compareAddedDate(LocalDate firstDate, LocalDate secondDate){
        int result = 0;
        if(firstDate.isAfter(secondDate)){
            result = 1;
        }
        else {
            result = - 1;
        }
    return result;
    }

}
