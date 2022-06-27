package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractBookBuilder;
import online.book.store.entity.Book;

import java.io.File;
import java.lang.reflect.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends AbstractBookBuilder {

    private File image;
    private String isbn;
    private String title;
    private String publisher;
    private double price;
    private String yearPub;
    private String subject;
    private String available;
    private int availableCopies;
    private String description;
    private String authors;
    private String format;


    @Override
    public AbstractBookBuilder builder() {
        return new BookDto();
    }

    @Override
    public AbstractBookBuilder path(File bookImage) {
        this.image = bookImage;
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
    public AbstractBookBuilder price(Double price) {
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
    public AbstractBookBuilder availableCopies(int availableCopies) {
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
    public boolean containsNull() {
        Field[] fields = this.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null) return true;
        }

        return false;
    }



    @Override
    public Book build() {
        if(!this.containsNull()){

            String absolutePath = this.getImage().getAbsolutePath();
            String pathToImage = absolutePath.substring(absolutePath.indexOf("images") - 1);

        return new Book(this.isbn, this.title,
                        this.publisher, this.price,
                        this.yearPub, this.subject, pathToImage, this.availableCopies, this.description, this.authors, this.format);
        }
    return null;
    }

    public Book doBookBuilder(){
        return this.builder().isbn(this.isbn).
                        title(this.title).publisher(this.publisher).
                        price(this.price).yearPub(this.yearPub).
                        subject(this.subject).path(this.image).
                        availableCopies(this.availableCopies).
                        description(this.description).
                        authors(this.authors).
                        format(this.format).
                        build();
    }
}
