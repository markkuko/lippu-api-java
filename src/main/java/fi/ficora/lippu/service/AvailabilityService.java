package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.AvailabilityRequest;
import fi.ficora.lippu.domain.model.AvailabilityResponse;
import fi.ficora.lippu.domain.model.TravelAvailability;
import fi.ficora.lippu.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Services for availability functionality.
 * @author markkuko
 */
@Service
public class AvailabilityService implements IAvailabilityService{



    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);

    public AvailabilityService() {

    }
    /**
     * Checks if there are available quota for given request and
     * then reservers it if there is quota available
     * @param request
     * @return
     */
    public AvailabilityResponse addAvailability(AvailabilityRequest request) {
        // @todo replace stub implementation
        // @todo validation checks only product type and contract

        if(productService.isValidProduct(request.getTravel().getProductType(), request.getContract())) {
            AvailabilityResponse response = new AvailabilityResponse();
            return  response;
        } else {
            return null;
        }
    }
}
