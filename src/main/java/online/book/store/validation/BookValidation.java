package online.book.store.validation;

import online.book.store.dto.BookDto;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class BookValidation extends AbstractValidation {

    @Autowired
    private ValidateResponse response;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(BookDto.class);
    }

    @Override
    public void validation(Object target) {
        if (!supports(target.getClass())) return ;
            deleteErrorsMessages();

            BookDto bookDto = (BookDto) target;

            String isbn = bookDto.getIsbn();

            if(isbn.isEmpty()){
                this.response.addError("isbn", "ISBN can't be empty");
            }

            Pattern isbnPat = Pattern.compile("(\\d-?){13}|(\\d-?){10}");

            if(!isbnPat.matcher(isbn).find()){
                this.response.addError("isbn", "Wrong ISBN format");
            }

            String title = bookDto.getTitle();

            if(title.isEmpty()){
                this.response.addError("title", "Title can't be empty");
            }

            Pattern titlePat = Pattern.compile("([a-zA-Z0-9\\-]+)");

            if(!titlePat.matcher(title).find()){
                this.response.addError("title", "Wrong title format");
            }

            String yearPub = bookDto.getYearPub();


            if(yearPub.isEmpty()){
                this.response.addError("year", "Year Of Publication can't be empty");
            }

            String yearRegExp = "^(19|20)\\d{2}$";
            if(!yearPub.matches(yearRegExp)){
                this.response.addError("year", "Wrong year format");
            }

            String subject = bookDto.getSubject();

            if(subject.isEmpty()){
                this.response.addError("subject", "Subject can't be empty");
            }

            String image = bookDto.getBookImage();

            if(image.isEmpty()){
                this.response.addError("image", "Image not selected");
            }

            if(!imageFile(bookDto.getBookImage())){
                this.response.addError("image", "Selected file not image");
            }

            String availableCopies = bookDto.getAvailableCopies();

            if(availableCopies.isEmpty()){
                this.response.addError("copies", "Available copies can't be empty");
            }

            Pattern availableCopiesPat = Pattern.compile("[0-9]");

            if(!availableCopiesPat.matcher(availableCopies).find()){
                this.response.addError("copies", "Must be integer");
            }

            String description = bookDto.getDescription();

            if(description.isEmpty()){
                this.response.addError("desc", "Description can't be empty");
            }

            int length = description.length();
            if(length < 200 || length > 1000){
                this.response.addError("desc", "Description min length 200, max 100");
            }

            String author = bookDto.getAuthors();

            if(author.isEmpty()){
                this.response.addError("author", "Authors can't be empty");
            }

            String format = bookDto.getFormat();

            if(format.isEmpty()){
                this.response.addError("format", "Format can't be empty");
            }

            String formatPattern = "^\\d{2,3}(x)\\d{2,3}";
            if(!format.matches(formatPattern)){
                this.response.addError("format", "Wrong format value");
            }

            String price = bookDto.getPrice();

            if(price.isEmpty()){
                this.response.addError("price", "Price can't be empty");
            }

            Pattern pricePat = Pattern.compile("[0-9]");

            if(!pricePat.matcher(price).find()) {
                this.response.addError("price", "Must be integer value");
            }

            String selectedCategories = bookDto.getBooksCategories();


            if(selectedCategories == null || selectedCategories.isEmpty()){
                this.response.addError("categories", "Categories not selected");
            }

            String publisher = bookDto.getPublisher();

            if(publisher.isEmpty()){
                this.response.addError("publisher", "Publisher can't be empty");
            }
    }


    private boolean imageFile(String name){
        if(name.isEmpty()) return false;
        String currentFormat = name.substring(name.indexOf("."));
        String[] imagesFormat = {".png", ".jpg", ".bmp", ".ico", ".jpeg", ".pnm"};
        for(String format : imagesFormat){
            if(currentFormat.equals(format)){
                return true;
            }
        }
    return false;
    }

    @Override
    public void deleteErrorsMessages() {
        this.response = new ValidateResponse();
    }

    @Override
    public Map<String, String> validationErrors() {
        return this.response.getFieldErrors();
    }

    @Override
    public boolean hasErrors() {
        return !this.response.getFieldErrors().isEmpty();
    }
}
