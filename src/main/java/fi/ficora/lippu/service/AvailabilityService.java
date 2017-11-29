package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.*;
import fi.ficora.lippu.exception.ProductNotFoundException;
import fi.ficora.lippu.exception.TimetableNotFoundException;
import fi.ficora.lippu.repository.CapacityRepository;
import fi.ficora.lippu.repository.TransportRepository;
import fi.ficora.lippu.util.ApiErrorUtil;
import fi.ficora.lippu.util.ConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    public Availability getSoftBookingAvailability(TravelRequest request,
                                                   List<TravelPassenger> passengers,
                                                   String contract)
        throws ProductNotFoundException, TimetableNotFoundException{
        // Get produdct
        Product product = productService.getProduct(request, contract);
        if(product == null) {
            throw new ProductNotFoundException("Product was not found.");
        }
        // Capasity check,
        Availability availability = new Availability();
        Reservation reservation = null;
        if(request.getDepartureTimeEarliest() == null)
            reservation = checkForCapacity(product,
                    request.getArrivalTimeLatest().toLocalDate(),
                    passengers);
        else
            reservation = checkForCapacity(product,
                    request.getDepartureTimeEarliest().toLocalDate(),
                    passengers);
        if(reservation == null) {
            return null;
        }

        List<TravelAvailability> availabilities = new ArrayList<TravelAvailability>();
        for (TravelPassenger passenger : passengers) {
            TravelAvailability item = addAvailability(
                    reservation,product, passenger, request);
            if(item != null) {
                availabilities.add(item);
            } else {
                log.debug("Got null for availabity add");
            }
        }
        availability.setProduct(product);
        availability.setAvailabilityList(availabilities);
        return availability;
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
                                           TravelRequest travel) {
        // @todo validation checks only product type and contract
        if(!(validPassengerAccessibilityAvailability(passenger, product)
                    && validPassengerExtraServiceAvailability(passenger, product))) {
            log.debug("Could not validate accessiblity or extra service," +
                    "returning null.");
            return null;
        }
        ReservationItem item = reservationService.createReservationItem(
                    product, reservation, travel, passenger);
        reservationService.addReservationItem(item);
        List<AccessibilityFeature> accessibilities=
                getAccessibilities(product, passenger.getAccessibility());
        List<ExtraServiceFeature> services=
                getExtraServices(product, passenger.getExtraServices());
        TravelAvailability availability = ConversionUtil.
                reservationItemToTravelAvailability(item, passenger, accessibilities, services);
        availability.fare(ConversionUtil.fareToProductFare(
                productService.getFare(product.getId())));
        availability.transport(ConversionUtil.transportToModelTransport(
                productService.getTransport(product.getId())));
        return availability;
    }

    private boolean validPassengerAccessibilityAvailability(
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
    private boolean validPassengerExtraServiceAvailability(
            TravelPassenger passenger, Product product) {
        if(passenger.getExtraServices() == null) {
            return true;
        }
        for(ExtraServiceBase service: passenger.getExtraServices()) {

            log.debug("Check if product {} has extraService {}",
                    product.getId(), service.getTitle());
            boolean valid = false;
            for(ExtraServiceFeature service1: product.getExtraServiceFeatures()) {
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
    private List<AccessibilityFeature> getAccessibilities(Product product,
                                                          List<? extends AccessibilityBase> accessibilities) {
        List<AccessibilityFeature> returnList = new ArrayList<>();
        if(accessibilities == null) {
            return returnList;
        }
        for(AccessibilityBase accessibility : accessibilities) {
            AccessibilityFeature a =
                    productService.getAccessibilityFromProduct(product,
                            accessibility.getTitle());
            if(a != null) {
                AccessibilityFeature feature = new AccessibilityFeature();
                feature.setTitle(a.getTitle());
                feature.setAdditionalInformation(a.getAdditionalInformation());
                feature.setDescription(a.getDescription());
                feature.setFare(a.getFare());
                feature.setAccessibilityReservationId(reservationService.
                        generateAccessiblityReservationCode(a));
                returnList.add(feature);
            }
        }
        return returnList;
    }
    private List<ExtraServiceFeature> getExtraServices(Product product, List<ExtraServiceBase>
            services) {
        List<ExtraServiceFeature> returnList = new ArrayList<>();
        if(services == null) {
            return returnList;
        }
        for(ExtraServiceBase extraService : services) {
            ExtraServiceFeature e =
                    productService.getExtraServiceFromProduct(product,
                            extraService.getTitle());
            if(e != null) {
                ExtraServiceFeature service = new ExtraServiceFeature();
                service.setTitle(e.getTitle());
                service.setDescription(e.getDescription());
                service.setFare(e.getFare());
                service.setExtraServiceReservationId(
                        reservationService.generateExtraServiceReservationCode(
                                e));
                returnList.add(service);
            }
        }
        return returnList;
    }

}
