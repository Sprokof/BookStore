package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractBookBuilder;
import online.book.store.entity.Book;
import online.book.store.entity.Category;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


@NoArgsConstructor
@Component
public class BookDto extends AbstractBookBuilder {

    public static final String[] AVAILABLE_STATUS = {"is available", "not available"};

    @Getter
    @Setter
    private String bookImage;

    @Getter
    @Setter
    private String isbn;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String publisher;

    @Getter
    @Setter
    private String price;

    @Getter
    @Setter
    private String yearPub;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String available;

    @Getter
    @Setter
    private String availableCopies;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String authors;

    @Getter
    @Setter
    private String format;

    @Getter
    @Setter
    private String booksCategories;


    public BookDto(String bookImage, String isbn, String title,
                   String publisher, String price, String yearPub,
                   String subject, String available, String availableCopies,
                   String description, String authors, String format, String booksCategories) {
        this.bookImage = bookImage;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.price = price;
        this.yearPub = yearPub;
        this.subject = subject;
        this.available = available;
        this.availableCopies = availableCopies;
        this.description = description;
        this.authors = authors;
        this.format = format;
        this.booksCategories = booksCategories;

    }


    // implementation of builder

    @Override
    public AbstractBookBuilder builder() {
        return new BookDto();
    }

    @Override
    public AbstractBookBuilder bookImage(String bookImageName) {
        this.bookImage = bookImageName;
        return this;
    }

    @Override
    public AbstractBookBuilder isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    @Override
    public AbstractBookBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public AbstractBookBuilder price(String price) {
        this.price = price;
        return this;
    }

    @Override
    public AbstractBookBuilder yearPub(String yearPub) {
        this.yearPub = yearPub;
        return this;
    }

    @Override
    public AbstractBookBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    @Override
    public AbstractBookBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public AbstractBookBuilder availableCopies(String availableCopies) {
        this.availableCopies = availableCopies;
        return this;
    }

    @Override
    public AbstractBookBuilder format(String format) {
        this.format = format;
        return this;
    }

    @Override
    public AbstractBookBuilder authors(String authors) {
        this.authors = authors;
        return this;
    }

    @Override
    public AbstractBookBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean containsNull() {
        Field[] fields = this.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null) return true;
        }
        return false;
    }

    @Override
    public AbstractBookBuilder categories(String categories) {
        this.booksCategories = categories;
        return this;
    }

    @Override
    public Book build() {
        Book book;
        if(!this.containsNull()){
            book = new Book(this.isbn, this.title,
                        this.publisher, Integer.parseInt(this.price),
                        Integer.parseInt(this.yearPub), this.subject, this.bookImage, AVAILABLE_STATUS[0],
                        Integer.parseInt(this.availableCopies), this.description, this.authors, this.format);

            addCategoryToBook(book);

        return book;
        }
    return null;
    }

    public Book doBookBuilder(){
        return this.builder().isbn(this.isbn).
                        title(this.title).publisher(this.publisher).
                        price(this.price).yearPub(this.yearPub).
                        subject(this.subject).bookImage(bookImage).
                        availableCopies(this.availableCopies).
                        description(this.description).
                        authors(this.authors).
                        format(this.format).
                        categories(this.booksCategories).
                        build();
    }


    private void addCategoryToBook(Book book){
        String[] categories = this.booksCategories.split("\\,");
        for(String category : categories){
            book.addCategory(new Category(category));
        }
    }

    @Override
    public String toString() {
        return "BookDto{" +
                ", bookImage='" + bookImage + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price='" + price + '\'' +
                ", yearPub='" + yearPub + '\'' +
                ", subject='" + subject + '\'' +
                ", available='" + available + '\'' +
                ", availableCopies='" + availableCopies + '\'' +
                ", description='" + description + '\'' +
                ", authors='" + authors + '\'' +
                ", format='" + format + '\'' + "";
    }


}
