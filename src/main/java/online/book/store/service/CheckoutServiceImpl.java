package online.book.store.service;

import online.book.store.dto.CheckoutDto;
import online.book.store.entity.Checkout;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutServiceImpl implements CheckoutService{

    @Autowired
    private UserService userService;


    @Override
    public void saveCheckoutInfo(CheckoutDto checkoutDto, User user) {
        Checkout checkout = checkoutDto.doCheckoutBuilder();
        String sessionid = checkoutDto.getSessionid();
        user.setCheckout(checkout);
        this.userService.updateUser(user);
    }
}
