package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.AvailabilityRequest;
import fi.ficora.lippu.domain.model.AvailabilityResponse;
import fi.ficora.lippu.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interface declaration for Availability functionality.
 *
 * @author markkuko
 */
public interface IAvailabilityService {

    /**
     * Checks if there are available quota for given request and
     * then reservers it if there is quota available
     * @param request
     * @return
     */
    public AvailabilityResponse addAvailability(AvailabilityRequest request);
}
