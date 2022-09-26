package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractBookBuilder;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends AbstractBookBuilder {

    public static final String[] AVAILABLE_STATUS = {"is available", "not available"};

    @Value("${images}")
    private String directoryLocation;

    @Value("${root}")
    private String root;

    private String bookImage;
    private String isbn;
    private String title;
    private String publisher;
    private double price;
    private String yearPub;
    private String subject;
    private String available;
    private String availableCopies;
    private String description;
    private String authors;
    private String format;
    private List<Category> booksCategory;




    // implementation of builder

    @Override
    public AbstractBookBuilder builder() {
        return new BookDto();
    }

    @Override
    public AbstractBookBuilder bookImage(String bookImageName) {
        findAndRewriteImage(bookImageName);
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
    public AbstractBookBuilder categories(List<Category> categories) {
        this.booksCategory = categories;
        return this;
    }

    @Override
    public Book build() {
        Book book;
        if(!this.containsNull()){
            book = new Book(this.isbn, this.title,
                        this.publisher, this.price,
                        this.yearPub, this.subject, this.bookImage, AVAILABLE_STATUS[0],
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
                        build();
    }

    private void findAndRewriteImage(String bookImage) {
        File root = new File(this.root);
        BufferedImage image = null;
        File[] files = root.listFiles();

        try {
            for (File file : files) {
                if (file.getName().equals(bookImage)) {
                    File path = new File(file.getAbsolutePath());
                    image = ImageIO.read(path);
                }
            }
            assert image != null;
            ImageIO.write(image, "jpg", new File(directoryLocation));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }


    private void addCategoryToBook(Book book){
        for(Category category : this.booksCategory){
            book.addCategory(category);
        }
    }
}
