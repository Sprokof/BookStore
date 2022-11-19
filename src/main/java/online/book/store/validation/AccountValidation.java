package online.book.store.validation;


import lombok.Getter;
import online.book.store.config.MailConfig;
import online.book.store.dto.UserDto;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class AccountValidation {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidateResponse response;

    @Component
    public class EmailValidation extends AbstractValidation {

        @Override
        public boolean supports(Class<?> aClass) {
            return aClass.equals(UserDto.class);
        }

        @Override
        public void validation(Object target) {
            if(!supports(target.getClass())) return ;
            deleteErrorsMessages();

            UserDto userDto = (UserDto) target;

            String email = userDto.getEmail();
            if(email.isEmpty()){
                AccountValidation.this.response.addError("new-email", "Can't be empty");
            }
            Pattern emailPattern = Pattern.compile(MailConfig.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

            if(!emailPattern.matcher(email).find()){
                AccountValidation.this.response.addError("new-email", "Wrong email format");

            }
            boolean emailExist = AccountValidation.this.userService.loginExist(email);

            if(emailExist){
                AccountValidation.this.response.addError("new-email", "Email already taken");
            }
        }

        @Override
        public boolean hasErrors() {
            return !AccountValidation.this.response.getFieldErrors().isEmpty();
        }

        @Override
        public void deleteErrorsMessages() {
            AccountValidation.this.response = new ValidateResponse();
        }

        @Override
        public Map<String, String> validationErrors() {
            return AccountValidation.this.response.getFieldErrors();
        }
    }


    @Component
    public class PasswordValidation extends AbstractValidation {
        @Override
        public boolean supports(Class<?> aClass) {
            return aClass.equals(UserDto.class);
        }

        @Override
        public void validation(Object target) {
            if(!supports(target.getClass())) return ;
            UserDto userDto = (UserDto) target;

            String password = userDto.getPassword();
            String confirmPassword = userDto.getConfirmPassword();

            validatePassword(AccountValidation.this.response, password, confirmPassword,
                    "new-password", "confirm-new-password");
        }

        @Override
        public void validatePassword(ValidateResponse response, String password,
                                     String confirmPassword, String passwordFieldId,
                                     String confirmPasswordFieldId) {
            super.validatePassword(response, password, confirmPassword,
                    passwordFieldId, confirmPasswordFieldId);
        }

        @Override
        public boolean hasErrors() {
            return !AccountValidation.this.response.getFieldErrors().isEmpty();
        }

        @Override
        public void deleteErrorsMessages() {
            AccountValidation.this.response = new ValidateResponse();
        }

        @Override
        public Map<String, String> validationErrors() {
            return AccountValidation.this.response.getFieldErrors();
        }
    }

}
