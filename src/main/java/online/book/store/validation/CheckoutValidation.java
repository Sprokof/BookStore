package online.book.store.validation;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import online.book.store.dto.CheckoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class CheckoutValidation extends AbstractValidation {

    @Value("${geo.key}")
    private String key;

    @Autowired
    private ValidateResponse response;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CheckoutDto.class);
    }

    @Override
    public void validation(Object target) {
        if(!supports(target.getClass())) return ;
        CheckoutDto checkoutDto = (CheckoutDto) target;
        deleteErrorsMessages();

        String firstName = checkoutDto.getFirstName();
        if(firstName.isEmpty()){
            this.response.addError("f-name", "Can't be empty");
        }

        String lastName = checkoutDto.getLastName();
        if(lastName.isEmpty()){
            this.response.addError("l-name", "Can't be empty");
        }

        String country = checkoutDto.getCountry();
        if(country.isEmpty()){
            this.response.addError("country", "Can't be empty");
        }

        String city = checkoutDto.getCity();
        if(city.isEmpty()){
            this.response.addError("city", "Can't be empty");
        }

        String street = checkoutDto.getStreet();
        if(street.isEmpty()){
            this.response.addError("address", "Can't be empty");
        }

        String address = checkoutDto.getAddress();
        if(!addressExist(address)){
            this.response.addError("address", "Address not exist " +
                    "(check also 'Country' and 'City' fields");
        }

        String zipCode = checkoutDto.getZip();
        if(zipCode.isEmpty()){
            this.response.addError("zip", "Can't be empty");
        }

        Pattern zipPattern = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");

        if(!zipPattern.matcher(zipCode).find()){
            this.response.addError("zip", "Wrong zip code");
        }

        String cardNumber = checkoutDto.getCardNumber().replaceAll("\\p{P}", "");

        if(cardNumber.isEmpty()){
            this.response.addError("card-num", "Can't be empty");
        }

        String cardNumberRegExp = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";

        if(!cardNumber.matches(cardNumberRegExp)){
            this.response.addError("card-num", "Wrong number, validate it");
        }

        String expDate = checkoutDto.getExp();

        if(expDate.isEmpty()){
            this.response.addError("exp", "Can't be empty");
        }

        String expDateRegExp = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$";

        if(!expDate.matches(expDateRegExp)){
            this.response.addError("exp", "Wrong exp. format");
        }

        String ccv = checkoutDto.getCcv();

        if(ccv.isEmpty()) {
            this.response.addError("ccv", "Can't be empty");
        }

        String cvvPattern = "\\d{3}";

        if(!ccv.matches(cvvPattern)){
            this.response.addError("ccv", "wrong ccv format");
        }

    }

    @Override
    public boolean addressExist(String address) {
        if(address.isEmpty()) return false;
        JOpenCageGeocoder cageGeocoder = new JOpenCageGeocoder(key);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
        request.setRestrictToCountryCode("ru");

        JOpenCageResponse response = cageGeocoder.forward(request);
        return !response.getResults().isEmpty();
    }

    @Override
    public Map<String, String> validationErrors() {
        return this.response.getFieldErrors();
    }

    @Override
    public boolean hasErrors() {
        return !this.response.getFieldErrors().isEmpty();
    }

    @Override
    public void deleteErrorsMessages() {
        this.response = new ValidateResponse();
    }
}
