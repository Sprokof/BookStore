package online.book.store.engines;

import online.book.store.entity.Book;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class SortEngine implements SiteEngine {

    public static String sortType = "Popularity";

    public List<Book> getSortBooks(String sortType, List<Book> allBooks) {
        switch (sortType) {
            case "Popularity":
                allBooks.sort(Comparator.comparingInt(Book::getBookRating));
                break;
            case "Low to High":
                allBooks.sort(Comparator.comparingDouble(Book::getPrice));
                break;
            case "High to Low":
                allBooks.sort((book, bookNext) -> {
                    return ((int) bookNext.getPrice() - (int) book.getPrice());
                });
                break;
            case "Latest":
                allBooks.sort(Comparator.comparingInt((b) -> Integer.parseInt(b.getYearPub())));
                break;

        }
        return allBooks;
    }

    public static String[] sortTypes(){
        return new String[]{"Popularity", "Low to High", "High to Low", "Latest"};
    }

}
