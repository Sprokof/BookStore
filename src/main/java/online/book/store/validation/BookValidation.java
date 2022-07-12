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

    }

    private boolean rightDirectory(String fileLocation){
        return fileLocation.contains("image");

    }
}
