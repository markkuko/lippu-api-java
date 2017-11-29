package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.domain.model.TravelRequest;
import fi.ficora.lippu.exception.NotAuthorizedException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if(item == null) {
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
        if(item == null) {
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
                                                    reservations) {
        List<ReservationItem> items = new ArrayList<>();
        String clientId = authService.getClientId();
        for(ReservationRequestReservations reservation: reservations) {
            ReservationItem item = reservationItemRepository.
                    findOneByTravelEntitlementId(reservation.getTravelEntitlementId());
            try {

                if (isValidReservation(item, reservation)) {
                    authService.verifyAuthorization(item);
                    item.setConfirmed(true);
                    item.setTicketPayload(ticketService.generateTicket(item));

                    items.add(reservationItemRepository.save(item));

                }
            } catch (NotAuthorizedException e) {
                log.info("Client {} tried to confirm item {}, message: {}", clientId,
                        item.getTravelEntitlementId(), e.getMessage());
            }
        }
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

    private boolean isValidReservation(ReservationItem item,
                                       ReservationRequestReservations reservation) {
        if(item == null || item.getReservationValidTo() == null) {
            log.info("Did not find reservation with travel entitlement id {}",
                    reservation.getTravelEntitlementId());
            return false;
        }

        if (!item.getReservationValidTo().isAfter(OffsetDateTime.now())) {
            log.info("Reservation {} has expired",
                    reservation.getTravelEntitlementId() );
            return false;
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
