package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.*;
import fi.ficora.lippu.repository.CapacityRepository;
import fi.ficora.lippu.repository.TransportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Services for availability functionality.
 * @author markkuko
 */
@Service
public class AvailabilityService implements IAvailabilityService{



    @Autowired
    private IProductService productService;

    @Autowired
    private ITimetableService timetableService;

    @Autowired
    private IReservationService reservationService;
    @Autowired
    private CapacityRepository capacityRepository;
    @Autowired
    private TransportRepository transportRepository;

    private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);

    public AvailabilityService() {

    }
    public Reservation checkForCapacity(Product product, LocalDate travelDate, int passengers) {

        Capacity travelCapacity = capacityRepository.findOneByProductId(
                product.getId());
        if(travelCapacity != null && (travelCapacity.getMaxCapacity() - (
                reservationService.getReservationCount(product, travelDate) - passengers) > 0)) {
            return reservationService.create();
        } else {
            return null;
        }
    }


    public ReservationItem addAvailability(Reservation reservation,
                                           Product product,
                                           TravelPassenger passenger,
                                           Travel travel) {
        // @todo validation checks only product type and contract
        if(validatePassengerAccessibilityAvailability(passenger, product)
                    && validatePassengerExtraServiceAvailability(passenger, product)) {

                ReservationItem item = reservationService.createReservationItem(
                        product, reservation, travel, passenger);
                return reservationService.addReservationItem(item);

        }
        return null;

    }

    private boolean validatePassengerAccessibilityAvailability(
            TravelPassenger passenger, Product product) {
        if(passenger.getAccessibility() == null) {
            return true;
        }
        for (Accessibility accessibility : passenger.getAccessibility()) {
            if (!accessibility.getTitle().equals(Accessibility.TitleEnum.UNKNOWN)) {
                log.debug("Check if product {} has accessibility {}",
                        product.getId(), accessibility.getTitle());
                boolean valid = false;
                for (Accessibility accessibility1 : product.getAccessibilities()) {
                    if (accessibility.getTitle().equals(accessibility1.getTitle())) {
                        // Found
                        log.debug("Did not find required accessibility ({}) for product {}",
                                accessibility.getTitle(), product.getId());
                        valid = true;
                        break;
                    }
                }
                // Check we found required accessibility
                if (!valid) {
                    log.info("Did not find required accessibility ({}) for product {}",
                            accessibility.getTitle(), product.getId());
                    return false;
                }
            }
        }
        return true;
    }
    private boolean validatePassengerExtraServiceAvailability(
            TravelPassenger passenger, Product product) {
        if(passenger.getExtraServices() == null) {
            return true;
        }
        for(ExtraService service: passenger.getExtraServices()) {

            log.debug("Check if product {} has extraService {}",
                    product.getId(), service.getTitle());
            boolean valid = false;
            for(ExtraService service1: product.getExtraServices()) {
                if(service.getTitle().equals(service1.getTitle())) {
                    // Found
                    log.debug("Found extra service {} in product {}.",
                            service.getTitle(), product.getId());
                    valid = true;
                    break;
                }
            }
            // Check we found required accessibility
            if(!valid) {
                log.info("Did not find required extra service ({}) for product {}",
                        service.getTitle(), product.getId());
                return false;
            }
        }

        return true;
    }


}
