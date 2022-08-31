package online.book.store.controllers;




import online.book.store.dto.PaymentDto;
import online.book.store.entity.Checkout;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import online.book.store.service.OrderService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
public class CheckoutController {

}
