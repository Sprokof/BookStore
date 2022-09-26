package online.book.store.validation;

import online.book.store.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

            String  yearRegExp = "^(19|20)\\d{2}$";
            if(!yearPub.matches(yearRegExp)){
                this.response.addError("year", "Wrong year format");
            }

            String subject = bookDto.getSubject();

            if(subject.isEmpty()){
                this.response.addError("subject", "Subject can't be empty");
            }

            Pattern subjectPat = Pattern.compile("([a-zA-Z0-9\\-]+)");

            if(subjectPat.matcher(subject).find()){
                this.response.addError("subject", "Wrong format");
            }

            if(bookDto.getBookImage().isEmpty()){
                this.response.addError("image", "Image not selected");
            }

            if(!imageFile(bookDto.getBookImage())){
                this.response.addError("image", "Selected file not image");
            }

            String availableCopies = bookDto.getAvailableCopies();

            if(availableCopies.isEmpty()){
                this.response.addError("copies", "Available copies");
            }

            Pattern availableCopiesPat = Pattern.compile("[^0-9]");

            if(availableCopiesPat.matcher(availableCopies).find()){
                this.response.addError("copies", "Must be integer");
            }

            String description = bookDto.getBookImage();

            if(description.isEmpty()){
                this.response.addError("desc", "Description can't be empty");
            }

            int length = description.length();
            if(length < 30 || length > 210){
                this.response.addError("desc", "Description min length 30, max 210");
            }

            String author = bookDto.getAuthors();

            if(author.isEmpty()){
                this.response.addError("author", "Authors can't be empty");
            }

            String format = bookDto.getFormat();

            if(format.isEmpty()){
                this.response.addError("format", "Format can't be empty");
            }

            String formatPattern = "[1-9]{2}\\p{P}[1-9]{3}\\/[1-9]";

            if(!format.matches(formatPattern)){
                this.response.addError("format", "Wrong format value");
            }
    }


    private boolean imageFile(String name){
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
