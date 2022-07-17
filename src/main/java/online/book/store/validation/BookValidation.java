package online.book.store.validation;

import online.book.store.dto.BookDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(BookDto.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!supports(o.getClass())) return ;

            BookDto bookDto = (BookDto) o;

            if(bookDto.getBookImage() == null){
                errors.rejectValue("bookImage", "Insert.book.image");
            }

            if(!imageFile(bookDto.getBookImage().getName())){
                errors.rejectValue("bookImage", "Not.image.format");
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
}
