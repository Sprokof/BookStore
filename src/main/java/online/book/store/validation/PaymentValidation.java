package online.book.store.validation;

import online.book.store.dto.PaymentDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PaymentValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PaymentDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(supports(target.getClass())) return ;

        PaymentDto paymentDto = (PaymentDto) target;

        String cardNumberPattern = "(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})";


        if(!paymentDto.getCardNumber().matches(cardNumberPattern)){
            errors.rejectValue("cardNumber", "Incorrect.card.number.format");
        }
    }
}
