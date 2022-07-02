package online.book.store.controllers;

import online.book.store.dto.PaymentDto;
import online.book.store.entity.Order;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import online.book.store.service.CartServiceImpl;
import online.book.store.service.OrderService;
import online.book.store.service.UserService;
import online.book.store.validation.PaymentValidation;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    @Qualifier("paymentValidation") Validator payment;

    @Autowired
    CartService cartService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    OrderService orderService;

    @ModelAttribute("payment")
    public PaymentDto getPayment(){
        return new PaymentDto();
    }

    @ModelAttribute("months")
    public List<String> month(){
      List<String> months = new ArrayList<>();

      int month = 1, lastMonthInYear = 12;

      while(month != lastMonthInYear) {
          String m = String.valueOf(month);
          if (m.length() == 1) {
              months.add("0" + m);
          }
          months.add(m);

          month++;
      }
      return months;
    }

    @ModelAttribute("years")
    public List<String> years() throws ParseException {
        List<String> years = new ArrayList<>();
        int year = LocalDate.now().getYear();

        for(int i = 0; i < 3; i ++){
            years.add(String.valueOf((year ++ )));
        }
        return years;
    }

    @GetMapping("/home/payment")
    public String payment(){
        return "payment";
    }


    @PostMapping("/home/payment")
    public String payment(@ModelAttribute("payment") @Valid PaymentDto paymentDto,
                          BindingResult bindingResult){
        payment.validate(paymentDto, bindingResult);
        if(bindingResult.hasErrors()){
            return "payment";

        }
        orderService.addOrders();
        cartService.clearCart();

        return "payment";
    }
}
