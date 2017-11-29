package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.domain.model.TravelRequest;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.exception.NotValidReservationRequest;
import fi.ficora.lippu.exception.ResourceNotFoundException;
import fi.ficora.lippu.repository.ReservationItemRepository;
import fi.ficora.lippu.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * Services for storing and deleting reservations.
 * @author markkuko
 */
@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ITimetableService timetableService;
    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService() {

    }


    public int delete(String caseId) throws NotAuthorizedException {
        Reservation reservation = repository.findOne(caseId);
        if(reservation == null) {
            return Constants.RESULT_CODE_NOT_FOUND;
        }
        authService.verifyAuthorization(reservation);

        List<ReservationItem> items = reservationItemRepository.findAllByCaseId(caseId);
        for(ReservationItem item: items){
            authService.verifyAuthorization(item);
            reservationItemRepository.delete(item);
        }
        repository.delete(caseId);
        return Constants.RESULT_CODE_SUCCESS;
    }

    public int deleteReservationItem(String travelEntitlementId)
            throws NotAuthorizedException, ResourceNotFoundException {

        ReservationItem item =
                reservationItemRepository.findOneByTravelEntitlementId(travelEntitlementId);
        if(item == null || !item.isConfirmed()) {
            throw new ResourceNotFoundException("Reservation item not" +
                    "found for :" + travelEntitlementId);
        }
        authService.verifyAuthorization(item);
        List<ReservationItem> items = reservationItemRepository.
                findAllByCaseId(item.getCaseId());
        reservationItemRepository.delete(item);
        if(items.size() == 1) {
            repository.delete(item.getCaseId());
        }
        return Constants.RESULT_CODE_SUCCESS;
    }

    public ReservationItem getReservationItem(String travelEntitlementId)
            throws NotAuthorizedException, ResourceNotFoundException{

        ReservationItem item =
                reservationItemRepository.findOneByTravelEntitlementId(
                        travelEntitlementId);
        if(item == null) {
            throw new ResourceNotFoundException("Reservation item not" +
                    "found for :" + travelEntitlementId);
        }
        authService.verifyAuthorization(item);
        return item;
    }

    public ReservationItem activateReservationItem(String travelEntitlementId)
            throws NotAuthorizedException, ResourceNotFoundException{

        ReservationItem item =
                reservationItemRepository.findOneByTravelEntitlementId(travelEntitlementId);
        if(item == null || !item.isConfirmed()) {
            throw new ResourceNotFoundException("Reservation item not" +
                    "found for :" + travelEntitlementId);
        }
        authService.verifyAuthorization(item);
        item.setActivated(true);
        reservationItemRepository.save(item);
        return item;
    }

    public Reservation create() {
        Reservation reservation = new Reservation();
        reservation.setClientId(authService.getClientId());
        reservation = repository.save(reservation);
        return reservation;
    }


    public List<ReservationItem> confirmReservation(List<ReservationRequestReservations>
                                                    reservations)
            throws NotAuthorizedException, NotValidReservationRequest,
                   ResourceNotFoundException{
        String clientId = authService.getClientId();
        for(ReservationRequestReservations r: reservations){
            if (!isValidReservation(r)) {
                log.info("Reservation item {} was not valid", r.getTravelEntitlementId());
                throw new NotValidReservationRequest("Reservation item was not valid:" +
                        r.getTravelEntitlementId());
            }
        }
        List<ReservationItem> items = new ArrayList<>();
        reservations.forEach(r -> {
            ReservationItem item = reservationItemRepository.
                    findOneByTravelEntitlementId(r.getTravelEntitlementId());
            item.setConfirmed(true);
            item.setTicketPayload(ticketService.generateTicket(item));
            if (item.getExtraServiceFeatures() != null &&
                    r.getChosenExtraReservationIds() != null) {
                item.getExtraServiceFeatures()
                        .keySet().retainAll(new HashSet<>(r.getChosenExtraReservationIds()));
            } else {
                item.setExtraServiceFeatures(new HashMap<>());
            }
            if (item.getAccessibilities() != null &&
                    r.getChosenAccessibilityReservationIds() != null) {
                item.getAccessibilities()
                        .keySet().retainAll(new HashSet<>(r.getChosenAccessibilityReservationIds()));
            } else {
                item.setAccessibilities(new HashMap<>());
            }

            items.add(reservationItemRepository.save(item));
        });
        return items;
    }
    public ReservationItem addReservationItem(ReservationItem item) {
        return reservationItemRepository.save(item);
    }
    public String createTravelEntitlementId() {
        return UUID.randomUUID().toString();
    }

    public long getReservationCount(Product product, LocalDate travelDate) {
        LocalDateTime from = travelDate.atTime(0,0,0);
        LocalDateTime to = travelDate.atTime(23,59,59);
        long result = reservationItemRepository.findByProductIdAndTravelDateBetween(product.getId(), from, to).size();
        log.debug("Reservations for product {} between date {} - {} is {}", product, from, to, result);
        return result;
    }

    /**
     * Validates reservation request against reservation item. Returns
     * true if reservation item is not null and still valid,
     * requested accessibility features are in reservation item,
     * requested extra services are in reservation item.
     * @param reservation A singe reservation request.
     * @return Returns true if the request is valid for the item.
     */
    private boolean isValidReservation(ReservationRequestReservations reservation)
        throws NotAuthorizedException, ResourceNotFoundException{
        ReservationItem item = reservationItemRepository.
                findOneByTravelEntitlementId(reservation.getTravelEntitlementId());
        if(item == null || item.getReservationValidTo() == null) {
            log.info("Did not find reservation with travel entitlement id {}",
                    reservation.getTravelEntitlementId());
            throw new ResourceNotFoundException("Did not find resource with" +
                    "travel entitlement id: " + reservation.getTravelEntitlementId());
        }
        authService.verifyAuthorization(item);
        if (!item.getReservationValidTo().isAfter(OffsetDateTime.now())) {
            log.info("Reservation {} has expired",
                    reservation.getTravelEntitlementId() );
            return false;
        }
        if(reservation.getChosenAccessibilityReservationIds() != null) {
            for (String s : reservation.getChosenAccessibilityReservationIds()) {
                if (item.getAccessibilities() == null ||
                        !item.getAccessibilities().containsKey(s)) {
                    log.info("Reservation {} accessibility {} was not found",
                            reservation.getTravelEntitlementId(),
                            s);
                    throw new ResourceNotFoundException("Accessibility requirement" +
                            " reservation id:"+ s + "was not found for " +
                            "travel entitlement id: " + reservation.getTravelEntitlementId());
                }
            }
        }
        if(reservation.getChosenExtraReservationIds() != null) {
            for(String s: reservation.getChosenExtraReservationIds()){
                if(item.getExtraServiceFeatures() == null ||
                        !item.getExtraServiceFeatures().containsKey(s)) {
                    log.info("Reservation {} extra service {} was not found",
                            reservation.getTravelEntitlementId(),
                            s);
                    throw new ResourceNotFoundException("Extra service requirement" +
                            " reservation id:"+ s + "was not found for " +
                            "travel entitlement id: " + reservation.getTravelEntitlementId());
                }
            }
        }
        return true;
    }
    public ReservationItem createReservationItem(Product product,
                                                 Reservation reservation,
                                                 TravelRequest travel,
                                                 TravelPassenger passenger) {
        LocalDate travelTime;
        if(travel.getDepartureTimeEarliest() ==null)
            travelTime = travel.getArrivalTimeLatest().toLocalDate();
        else
            travelTime = travel.getDepartureTimeEarliest().toLocalDate();

        ReservationItem item = new ReservationItem();
        item.setPassengerCategory(passenger.getCategory());
        item.setConfirmed(false);
        item.setTravelEntitlementId(createTravelEntitlementId());
        item.setProductId(product.getId());
        item.setTravelDate(travelTime.atTime(12, 0));
        item.setReservationValidTo(OffsetDateTime.now().plusMinutes(
                Constants.RESERVATION_AVAILABILITY_MINUTES));
        item.setCaseId(reservation.getCaseId());
        item.setClientId(reservation.getClientId());
        item.setValidFrom(timetableService.getProductDeparture(
                travelTime, product));
        item.setValidTo(item.getValidFrom());
        return item;
    }


    public String generateExtraServiceReservationCode(ExtraServiceFeature service) {
        return "EXTRA-" + service.getTitle() + "-" +
                UUID.randomUUID().toString();
    }
    public String generateAccessiblityReservationCode(AccessibilityFeature service) {
        return "EXTRA-" + service.getTitle() + "-" +
                UUID.randomUUID().toString();
    }
}
