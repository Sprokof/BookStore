package online.book.store.service;

import online.book.store.dto.CheckoutDto;
import online.book.store.entity.User;

public interface CheckoutService {
    void saveCheckoutInfo(CheckoutDto checkoutDto, User user);
}
