package online.book.store.validation;

import online.book.store.dto.PaymentDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@Component
public class PaymentValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PaymentDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (supports(target.getClass())) return;

        PaymentDto paymentDto = (PaymentDto) target;


        String cardNumberPattern = "(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})";


        if (!paymentDto.getCardNumber().matches(cardNumberPattern)) {
            errors.rejectValue("cardNumber", "Incorrect.card.number.format");
        }
        if(paymentDto.getExpiryMonth().isEmpty() || paymentDto.getExpiryYear().isEmpty()){
            paymentDto.setValidDate(false);
        }
        else {
            String expiryDate = (paymentDto.getExpiryMonth() + "/" + paymentDto.getExpiryYear());
            if(!compareDates(expiryDate)){
                paymentDto.setValidDate(false);
            }
                paymentDto.setValidDate(true);
            }
    }

    private boolean compareDates(String expiryDate) {
        Date expDate = null;
        Date currentDate = null;
        boolean compareResult = false;
    try {
        expDate = toDate(expiryDate);
        currentDate = currentDate();
        compareResult = (expDate.after(currentDate));
    }
    catch (ParseException e){
        e.printStackTrace();
    }

    return compareResult;
    }



    private Date toDate(String expiryDate) throws ParseException {
        String currentDate = LocalDate.now().toString();
        String currentDay = currentDate.substring(currentDate.lastIndexOf("-") + 1);
        return new SimpleDateFormat("dd/MM/yyyy").
                parse(String.format("%s/%s", currentDay, expiryDate));
    }

    private Date currentDate() throws ParseException{
        String tempDate = LocalDate.now().toString();
        String[] dateArray = tempDate.split("-");
        return new SimpleDateFormat("dd/MM/yyyy").
                parse(String.format("%s/%s/%s", dateArray[2], dateArray[1], dateArray[0]));
    }

}
