package online.book.store.validation;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractValidation {

    @Autowired
    private ValidateResponse response;

    public boolean supports(Class<?> aClass){ return false; }

    public void validation(Object target){}

    public boolean hasErrors(){ return false; }

    public Map<String, String> validationErrors(){ return null; }

    public void deleteErrorsMessages() {
        if (!hasErrors()) return;
        Map<String, String> errors = this.response.getFieldErrors();
        for (Map.Entry<String, String> entry : errors.entrySet()) {
            errors.remove(entry.getKey());
        }
    }
}
