package online.book.store.controllers;




import online.book.store.dto.PaymentDto;
import online.book.store.dto.ShipmentDto;
import online.book.store.entity.Shipment;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class CheckoutController {

    private static final Map<String, List<String>> states = new HashMap<>();
    private static final Map<String, List<String>> cities = new HashMap<>();

    static {
       states.put("Russia", List.of("Udmurtia", "Tatarstan", "Bashkiria", "Not select"));
    }

    static {
        cities.put("Udmurtia", List.of("Izhevsk", "Votkinsk", "Glazov"));
        cities.put("Tatarstan", List.of("Kazan", "Naberezhnye Chelny", "Nizhnekamsk"));
        cities.put("Bashkiria", List.of("Ufa", "Salavat"));
        cities.put("Not selected", List.of("Moscow", "Saint Petersburg", "Samara", "Saratov"));
    }


    @Autowired
    Validator shipment;

    @Autowired
    HttpSession httpSession;

    @Autowired
    UserService userService;

    @ModelAttribute("shipment")
    public ShipmentDto getShipment(){
        return new ShipmentDto();
    }

    @ModelAttribute("countries")
    public List<String> countries(){
        return List.of("Russia");
    }

    @ModelAttribute("states")
    public Map<String, List<String>> states(){
        return states;
    }

    @ModelAttribute("cities")
    public Map<String, List<String>> cities(){
        return cities;
    }


    @GetMapping("/home/checkout")
    public String shipment() {
        if (userService.getCurrentUser().getShipment() != null) {
            return "redirect:/payment";
        }
    return "redirect:/shipment";
    }



    @PostMapping("/home/shipment")
    public String shipment(@ModelAttribute("shipment") @Valid ShipmentDto shipmentDto,
                           BindingResult result){
        shipment.validate(shipmentDto, result);

        if(result.hasErrors()){
            return "shipment";
        }
        if(shipmentDto.getSaveShipmenInfo().equals("true")){
            User currentUser = ((User) httpSession.getAttribute("user"));
            Shipment shipment = shipmentDto.doShipmentBuilder();
            currentUser.setShipment(shipment);
            userService.updateUser(currentUser);

        }
        return "payment";

    }


    @Autowired
    @Qualifier("paymentValidation") Validator payment;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @ModelAttribute("payment")
    public PaymentDto getPayment(){
        return new PaymentDto();
    }

    @ModelAttribute("months")
    public List<String> months(){
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
    public List<String> years() {
        List<String> years = new ArrayList<>();
        int year = LocalDate.now().getYear();

        for(int i = 0; i < 4; i ++){
            years.add(String.valueOf((year ++ )));
        }
        return years;
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
