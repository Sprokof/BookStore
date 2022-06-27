package online.book.store.controllers;

import online.book.store.dto.ShipmentDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ShipmentController {

    @ModelAttribute("shipment")
    public ShipmentDto getShipment(){
        return new ShipmentDto();
    }

    @GetMapping("/home/shipment")
    public ShipmentDto
}
