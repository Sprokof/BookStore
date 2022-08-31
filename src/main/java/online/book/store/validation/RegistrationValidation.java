package online.book.store.validation;

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
            this.response.addError("username", "Username must not be empty");
        }

        String usernamePattern = "^[a-z0-9]{3,10}$";
        if ((userDto.getUsername().matches(usernamePattern))) {
            if (userService.getUserByLogin(userDto.getUsername()) != null) {
                this.response.addError("username", "Username already taken");
            }
        } else {
            this.response.addError("username", "Username length must be " +
                    "between 3 and 10 characters and not contains special symbols.");
        }

        if (userService.getUserByLogin(userDto.getEmail()) != null) {
            this.response.addError("reg-email", "Email already taken");
        }

        if (userDto.getPassword().isEmpty()) {
            this.response.addError("reg-password", "Password must not be empty");
        }

        Pattern passwordPattern = Pattern.
                compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

        if (!passwordPattern.matcher(userDto.getPassword()).find()) {
            this.response.addError("reg-password", "Password must not contains whitespaces, " +
                    " must contains at least one digit," +
                    "  one letter in lower case and in upper case, must contains at least one special symbols," +
                    "  and be between 8 and 20 characters. ");
        }

        if (userDto.getConfirmPassword().isEmpty()) {
            this.response.addError("confirm-reg-password", "Confirm password must not be empty");
        } else {
            String password = userDto.getPassword(), confirmPassword = userDto.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                this.response.addError("confirm-reg-password", "Passwords not equals");
            }
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
        super.deleteErrorsMessages();
    }
}
