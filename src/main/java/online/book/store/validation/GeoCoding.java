package online.book.store.validation;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import online.book.store.dto.CheckoutDto;
import org.springframework.stereotype.Component;

@Component
public class GeoCoding {

    public boolean exist(CheckoutDto checkoutDto){
        JOpenCageGeocoder cageGeocoder = new JOpenCageGeocoder("3da994460a4f455eb741d58a03e97f43");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(checkoutDto.getAddress());
        request.setRestrictToCountryCode("ru");

        JOpenCageResponse response = cageGeocoder.forward(request);
        return response.getResults().isEmpty();
    }
}
