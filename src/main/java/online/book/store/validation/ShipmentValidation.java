package online.book.store.validation;

import online.book.store.dto.ShipmentDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


@Component
public class ShipmentValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
       return clazz.equals(ShipmentDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(!supports(target.getClass())) return ;

        ShipmentDto shipmentDto = (ShipmentDto) target;

        String firstName = "[A-Za-z]{3,20}";
        if(!shipmentDto.getFirstName().matches(firstName)){
            errors.rejectValue("firstName", "Incorrect.name");
        }

        String lastName = "[A-Za-z]{3,30}";
        if(!shipmentDto.getLastName().matches(lastName)){
            errors.rejectValue("lastName", "Incorrect.lastName");
        }

        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        if(!emailPattern.matcher(shipmentDto.getEmail()).find()){
            errors.rejectValue("email", "Incorrect.email");
        }

        String phonePattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if(!shipmentDto.getPhoneNumber().matches(phonePattern)){
            errors.rejectValue("phoneNumber", "Incorrect.phoneNumber");
        }
        if(shipmentDto.getCountry().isEmpty()){
            errors.rejectValue("country", "Country.empty");
        }
        if(shipmentDto.getState().isEmpty()){
            errors.rejectValue("state", "State.empty");
        }
        if(shipmentDto.getCity().isEmpty()){
            errors.rejectValue("city", "City.empty");
        }

    }
}
