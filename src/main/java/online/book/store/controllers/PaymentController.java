package online.book.store.controllers;

import online.book.store.dto.PaymentDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PaymentController {

    @ModelAttribute("payment")
    public PaymentDto getPayment(){
        return new PaymentDto();
    }

    @GetMapping("/home/payment")
    public String payment(){
        return "payment";
    }

    @PostMapping("/home/payment")
    public String payment(@ModelAttribute("payment") @Valid PaymentDto paymentDto, BindingResult bindingResult){
        return "payment";
    }
}
