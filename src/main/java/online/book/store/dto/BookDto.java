package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractBookBuilder;
import online.book.store.entity.Book;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends AbstractBookBuilder {

    public static final String[] AVAILABLE_STATUS = {"Is available", "Not available"};

    @Value("book.images.root")
    private String directoryLocation;

    private File bookImage;
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
    public AbstractBookBuilder bookImage(File bookImage) {
        this.bookImage = bookImage;

    try {
        writeImage(bookImage);
    }
    catch (IOException e){ e.printStackTrace(); }

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
            return new Book(this.isbn, this.title,
                        this.publisher, this.price,
                        this.yearPub, this.subject, this.bookImage.getName(),
                        this.availableCopies, this.description, this.authors, this.format);
        }
    return null;
    }

    public Book doBookBuilder(){
        return this.builder().isbn(this.isbn).
                        title(this.title).publisher(this.publisher).
                        price(this.price).yearPub(this.yearPub).
                        subject(this.subject).bookImage(null).
                        availableCopies(this.availableCopies).
                        description(this.description).
                        authors(this.authors).
                        format(this.format).
                        build();
    }

    private void writeImage(File bookImage) throws IOException {
        BufferedImage image = ImageIO.read(bookImage);
        ImageIO.write(image, "png", new File(directoryLocation));
    }
}
