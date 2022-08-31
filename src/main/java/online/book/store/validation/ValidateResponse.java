package online.book.store.validation;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@Getter
public class ValidateResponse {
    private final Map<String, String> fieldErrors = new TreeMap<>();

    public void addError(String field, String errorMessage){
        fieldErrors.putIfAbsent(field, errorMessage);
    }

}
