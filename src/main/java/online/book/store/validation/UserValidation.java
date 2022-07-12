package online.book.store.validation;

import online.book.store.dto.UserLoginDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


@Component
public class UserValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserLoginDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(supports(target.getClass())) return;

        UserLoginDto userLoginDto = (UserLoginDto) target;

        Pattern mailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        String userEmail = userLoginDto.getEmail();

        if(!mailPattern.matcher(userEmail).find()){
            errors.rejectValue("email", "Incorrect.email");
        }

        if(!userLoginDto.getConfirmCode().equals(userLoginDto.getGeneratedCode())){
            errors.rejectValue("confirmCode", "Code.not.equals");
        }
    }


}
