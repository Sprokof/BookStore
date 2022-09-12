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
        System.out.println(password);

        if (password.isEmpty()) {
            this.response.addError("reg-password", "Password can't be empty");
        }

        if (password.length() < 8 || password.length() > 15) {
            this.response.addError("reg-password","Password must be between 8 and 15 characters");
        }

        Pattern pattern = Pattern.compile("[a-z]");

        if (!pattern.matcher(password).find()) {
            this.response.addError("reg-password","Password must contains at least one letter");
        }

        pattern = Pattern.compile("[0-9]");

        if (!pattern.matcher(password).find()) {
            this.response.addError("reg-password", "Password must contains at least one digit");
        }

        if(userSignInDto.getConfirmPassword().isEmpty()){
            this.response.addError("confirm-reg-password", "Confirm password can't be empty");
        }

        if(!userSignInDto.getConfirmPassword().equals(userSignInDto.getPassword())){
            this.response.addError("confirm-reg-password", "Passwords not equals");
        }
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


}
