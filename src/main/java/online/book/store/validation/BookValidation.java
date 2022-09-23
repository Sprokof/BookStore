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

            if(bookDto.getBookImage() == null){
                this.response.addError("image", "Image not selected");
            }

            if(!imageFile(bookDto.getBookImage().getName())){
                this.response.addError("image", "Selected file not image");
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
