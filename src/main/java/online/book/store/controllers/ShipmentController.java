package online.book.store.controllers;




import online.book.store.dto.ShipmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.*;

@Controller
public class ShipmentController {

    private static Map<String, List<String>> states = new HashMap<>();
    private static Map<String, List<String>> cities = new HashMap<>();

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


    @GetMapping("/home/shipment")
    public String shipment(){
        return "shipment";
    }


    @PostMapping("/home/shipment")
    public String shipment(@ModelAttribute("shipment") @Valid ShipmentDto shipmentDto,
                           BindingResult result){
        shipment.validate(shipmentDto, result);

        if(result.hasErrors()){
            return "shipment";
        }

        return "payment";


    }

}
