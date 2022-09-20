package online.book.store.validation;

import lombok.Getter;
import online.book.store.dto.ResetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResetValidation extends AbstractValidation {

    @Component
    public static class ConfirmValidation extends ResetValidation{

        @Autowired
        @Getter
        private ValidateResponse response;


        @Override
        public boolean supports(Class<?> aClass) {
            return aClass.equals(ResetDto.class);
        }

        @Override
        public void deleteErrorsMessages() {
            this.response = new ValidateResponse();
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
        public void validation(Object target) {
            if(!supports(target.getClass())) return ;
            deleteErrorsMessages();

            ResetDto resetDto = ((ResetDto) target);

            String generatedCode = resetDto.getGeneratedCode();

            String inputCode = resetDto.getInputCode();

            if(inputCode.isEmpty()){
                this.response.addError("code", "Confirmation code can't be empty");
            }

            if(!inputCode.equals(generatedCode)){
                this.response.addError("code", "Wrong code");
            }

        }
    }

    @Autowired
    @Getter
    private ValidateResponse response;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ResetDto.class);
    }

    @Override
    public void validation(Object target) {
        if (!supports(target.getClass())) return;
        deleteErrorsMessages();

        ResetDto dto = (ResetDto) target;

        String password = dto.getNewPassword();
        String confirmPassword = dto.getConfirmResetPassword();

        validatePassword(this.response, password, confirmPassword, "new-password",
                "confirm-reset-password");

    }

    @Override
    public boolean hasErrors() {
        return !this.response.getFieldErrors().isEmpty();
    }

    @Override
    public void deleteErrorsMessages() {
        this.response = new ValidateResponse();
    }

    @Override
    public Map<String, String> validationErrors() {
        return this.response.getFieldErrors();
    }

    @Override
    public void validatePassword(ValidateResponse response, String password, String confirmPassword,
                                 String passwordFieldId, String confirmPasswordFieldId) {
        super.validatePassword(response, password, confirmPassword, passwordFieldId, confirmPasswordFieldId);
    }
}


