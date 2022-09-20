package online.book.store.validation;

import online.book.store.dto.UserDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class RegistrationValidation extends AbstractValidation {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidateResponse response;


    @Override
    public boolean supports(Class<?> aClass) {
        return UserSignInDto.class.equals(aClass);
    }

    @Override
    public void validation(Object target) {
        if (!supports(target.getClass())) return;
        UserSignInDto userSignInDto = (UserSignInDto) target;
        deleteErrorsMessages();

        if (userSignInDto.getUsername().isEmpty()) {
            this.response.addError("username", "Username can't be empty");
        }

        if (userService.getUserByLogin(userSignInDto.getUsername()) != null) {
            this.response.addError("username", "Username already taken");
        }

        if(userSignInDto.getEmail().isEmpty()){
            this.response.addError("reg-email", "Email can't be empty");
        }

        if (userService.getUserByLogin(userSignInDto.getEmail()) != null) {
            this.response.addError("reg-email", "Email already taken");
        }

        String password = userSignInDto.getPassword();

        String confirmPassword  = userSignInDto.getConfirmPassword();

        validatePassword(this.response, password, confirmPassword,
                "reg-password", "confirm-reg-password");

    }

    @Override
    public boolean hasErrors() {
        return !this.response.getFieldErrors().isEmpty();
    }

    @Override
    public Map<String, String> validationErrors() {
        return this.response.getFieldErrors();
    }

    @Override
    public void deleteErrorsMessages() {
        this.response = new ValidateResponse();
    }

    @Override
    public void validatePassword(ValidateResponse response, String password, String confirmPassword,
                                 String passwordFieldId, String confirmPasswordFieldId) {
        super.validatePassword(response, password, confirmPassword, passwordFieldId, confirmPasswordFieldId);
    }
}
