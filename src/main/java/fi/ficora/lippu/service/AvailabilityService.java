package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.*;
import fi.ficora.lippu.repository.CapacityRepository;
import fi.ficora.lippu.repository.TransportRepository;
import fi.ficora.lippu.util.ConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public Reservation checkForCapacity(Product product, LocalDate travelDate,
                                        List<TravelPassenger> passengers) {
        // @todo take into acccount extra services and accessibility
        // for capasity.
        Capacity travelCapacity = capacityRepository.findOneByProductId(
                product.getId());
        if(travelCapacity != null && (travelCapacity.getMaxCapacity() - (
                reservationService.getReservationCount(product, travelDate) -
                        passengers.size()) > 0)) {
            return reservationService.create();
        } else {
            return null;
        }
    }


    public TravelAvailability addAvailability(Reservation reservation,
                                           Product product,
                                           TravelPassenger passenger,
                                           Travel travel) {
        // @todo validation checks only product type and contract
        if(validatePassengerAccessibilityAvailability(passenger, product)
                    && validatePassengerExtraServiceAvailability(passenger, product)) {

            ReservationItem item = reservationService.createReservationItem(
                        product, reservation, travel, passenger);
            reservationService.addReservationItem(item);
            List<Accessibility> accessibilities=
                    getAccessibilities(product, passenger.getAccessibility());
            List<ExtraService> services=
                    getExtraServices(product, passenger.getExtraServices());
            TravelAvailability availability = ConversionUtil.
                    reservationItemToTravelPassenger(item, passenger, accessibilities, services);
            availability.fare(ConversionUtil.fareToProductFare(
                    productService.getFare(product.getId())));
            availability.transport(ConversionUtil.transportToModelTransport(
                    productService.getTransport(product.getId())));
            return availability;

        } else {
            log.debug("Could not validate accessiblity or extra service," +
                    "returning null.");
            return null;
        }


    }

    private boolean validatePassengerAccessibilityAvailability(
            TravelPassenger passenger, Product product) {
        if(passenger.getAccessibility() == null) {
            return true;
        }
        if(productService.hasRequiredAccessibityFeatures(product,
                passenger.getAccessibility())) {
            return true;
        } else {
            return false;
        }
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
            // Check if we found required extraservice
            if(!valid) {
                log.info("Did not find required extra service ({}) for product {}",
                        service.getTitle(), product.getId());
                return false;
            }
        }

        return true;
    }
    private List<Accessibility> getAccessibilities(Product product, List<Accessibility>
            accessibilities) {
        List<Accessibility> returnList = new ArrayList<>();
        if(accessibilities == null) {
            return returnList;
        }
        for(Accessibility accessibility : accessibilities) {
            Accessibility accessibility1 =
                    productService.getAccessibilityFromProduct(product,
                            accessibility.getTitle());
            if(accessibility1 != null) {
                returnList.add(accessibility1);
            }
        }
        return returnList;
    }
    private List<ExtraService> getExtraServices(Product product, List<ExtraService>
            services) {
        List<ExtraService> returnList = new ArrayList<>();
        if(services == null) {
            return returnList;
        }
        for(ExtraService extraService : services) {
            ExtraService extraService1 =
                    productService.getExtraServiceFromProduct(product,
                            extraService.getTitle());
            if(extraService1 != null) {
                ExtraService service = new ExtraService()
                        .title(extraService1.getTitle())
                        .description(extraService1.getDescription())
                        .fare(extraService1.getFare())
                        .extraServiceReservationData(
                        reservationService.generateExtraServiceReservationCode(
                                extraService1));
                returnList.add(service);
            }
        }
        return returnList;
    }

}
