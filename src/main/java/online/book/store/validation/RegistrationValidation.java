package online.book.store.validation;

import online.book.store.config.MailConfig;
import online.book.store.dto.UserDto;
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
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validation(Object target) {
        if (!supports(target.getClass())) return;
        UserDto userDto = (UserDto) target;
        deleteErrorsMessages();

        if (userDto.getUsername().isEmpty()) {
            this.response.addError("username", "Username can't be empty");
        }

        if (userService.loginExist(userDto.getUsername())) {
            this.response.addError("username", "Username already taken");
        }
        String email = userDto.getEmail();
        if(userDto.getEmail().isEmpty()){
            this.response.addError("reg-email", "Email can't be empty");
        }

        Pattern emailPattern = Pattern.compile(MailConfig.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        if(emailPattern.matcher(email).find()){
            this.response.addError("reg-email", "Wrong email format");
        }

        if (userService.loginExist(userDto.getEmail())) {
            this.response.addError("reg-email", "Email already taken");
        }

        String password = userDto.getPassword();

        String confirmPassword  = userDto.getConfirmPassword();

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
