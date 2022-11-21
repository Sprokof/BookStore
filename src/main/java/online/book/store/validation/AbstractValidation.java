package online.book.store.validation;

import online.book.store.service.UserService;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractValidation {


    public boolean supports(Class<?> aClass){ return false; }

    public void validation(Object target){}

    public boolean hasErrors(){ return false; }

    public Map<String, String> validationErrors(){ return null; }

    public void deleteErrorsMessages() {
    }

    public boolean addressExist(String address) {
        return false;
    }

    public void validatePassword(ValidateResponse response, String password, String confirmPassword,
                                 String passwordFieldId, String confirmPasswordFieldId){
        if (password.isEmpty()) {
            response.addError(passwordFieldId, "Password can't be empty");
        }

        if (password.length() < 8 || password.length() > 15) {
            response.addError(passwordFieldId,"Password must be between 8 and 15 characters");
        }

        Pattern pattern = Pattern.compile("[a-z]");

        if (!pattern.matcher(password).find()) {
            response.addError(passwordFieldId,"Password must contains at least one letter");
        }

        pattern = Pattern.compile("[0-9]");

        if (!pattern.matcher(password).find()) {
            response.addError(passwordFieldId, "Password must contains at least one digit");
        }

        if(confirmPassword.isEmpty()){
            response.addError(confirmPasswordFieldId, "Confirm password can't be empty");
        }

        if(!confirmPassword.equals(password)){
            response.addError(confirmPasswordFieldId, "Passwords not equals");
        }

    }

    public void validateUsername(UserService userService, ValidateResponse response,
                                 String username, String usernameFieldId){
        if(username.isEmpty()){
            response.addError(usernameFieldId, "Username can't be empty");
        }

        if (userService.loginExist(username)) {
            response.addError(usernameFieldId, "Username already taken");
        }

        Pattern usernamePattern = Pattern.compile("^[A-Za-z]\\w{5,19}$");
        if(!usernamePattern.matcher(username).find()){
            response.addError(usernameFieldId, "Username must starts " +
                    "from uppercase letter and be between 6 and 20 characters");
        }

    }
}
