package online.book.store.validation;

import online.book.store.dto.UserSignInDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractValidation {

    public boolean supports(Class<?> aClass){ return false; }

    public void validation(Object target){}

    public boolean hasErrors(){ return false; }

    public Map<String, String> validationErrors(){ return null; }

    public void deleteErrorsMessages() {
    }

    public void addUserDto(UserSignInDto userDto){
    }

    public UserSignInDto getUserDto () {
        return null;
    }
}
