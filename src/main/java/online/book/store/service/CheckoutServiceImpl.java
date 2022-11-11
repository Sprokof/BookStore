package online.book.store.service;

import online.book.store.dao.CheckoutDao;
import online.book.store.dto.CheckoutDto;
import online.book.store.entity.Checkout;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutServiceImpl implements CheckoutService{

    @Autowired
    private UserService userService;

    @Autowired
    private CheckoutDao checkoutDao;

    @Override
    public void saveCheckoutInfo(CheckoutDto checkoutDto, User user) {
        Checkout checkout = checkoutDto.doCheckoutBuilder();
        if(!checkoutSaved(user)) {
            user.setCheckout(checkout);
            this.userService.updateUser(user);
        }
        else {
            this.checkoutDao.updateCheckout(checkout);
        }
    }

    @Override
    public boolean checkoutSaved(User user) {
       return user.getCheckout() != null;
    }

    @Override
    public CheckoutDto getCheckoutsData(User user) {
        Checkout checkout = user.getCheckout();
        String address = checkout.getAddress();
        String street = extractStreetField(address);
        String city = extractCityField(address);
        String country = extractCountryField(address);

        CheckoutDto checkoutDto = new CheckoutDto();

        checkoutDto.setFirstName(checkout.getFirstName());
        checkoutDto.setLastName(checkout.getLastName());
        checkoutDto.setStreet(street);
        checkoutDto.setCity(city);
        checkoutDto.setCountry(country);
        checkoutDto.setZip(checkout.getZip());
        checkoutDto.setCardNumber(checkout.getNumber());
        checkoutDto.setExp(checkout.getExp());
        checkoutDto.setCcv("");

        return checkoutDto;
    }

    private String extractStreetField(String checkoutAddress){
        String [] addressComponents = checkoutAddress.split(",");
        if(addressComponents.length == 4){
            return String.format("%s, %s", addressComponents[0], addressComponents[1].trim());
        }
        return String.format("%s", addressComponents[0]);
    }

    private String extractCityField(String checkoutAddress) {
        String[] addressComponents = checkoutAddress.split(",");
        if (addressComponents.length == 4) {
            return addressComponents[2].trim();
        }
        return addressComponents[1].trim();
    }

    private String extractCountryField(String checkoutAddress){
        String[] addressComponents = checkoutAddress.split(",");
        if (addressComponents.length == 4) {
            return addressComponents[3].trim();
        }
        return addressComponents[2].trim();
    }
}
