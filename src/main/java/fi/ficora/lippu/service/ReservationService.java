package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.ReservationRequest;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.repository.ReservationItemRepository;
import fi.ficora.lippu.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return Constants.RESULTCODE_NOT_FOUND;
        }
        authService.verifyAuthorization(reservation);

        List<ReservationItem> items = reservationItemRepository.findAllByCaseId(caseId);
        for(ReservationItem item: items){
            reservationItemRepository.delete(item);
        }
        repository.delete(caseId);
        return Constants.RESULTCODE_SUCCESS;
    }



    public Reservation create() {
        Reservation reservation = new Reservation();
        reservation.setClientId(authService.getClientId());
        reservation = repository.save(reservation);
        return reservation;
    }


    public List<ReservationItem> confirmReservation(List<ReservationRequestReservations>
                                                    reservations) {
        List<ReservationItem> items = new ArrayList<ReservationItem>();
        String clientId = authService.getClientId();
        for(ReservationRequestReservations reservation: reservations) {
            ReservationItem item = reservationItemRepository.
                    findOneByReservationData(reservation.getReservationData());
            try {

                if (isValidReservation(item, reservation)) {
                    authService.verifyAuthorization(item);
                    item.setConfirmed(true);
                    item.setTicketPayload(ticketService.generateTicket(item));

                    items.add(reservationItemRepository.save(item));

                }
            } catch (NotAuthorizedException e) {
                log.info("Client {} tried to confirm item {}, message: {}", clientId,
                        item.getReservationData(), e.getMessage());
            }
        }
        return items;
    }
    public ReservationItem addResevationItem(ReservationItem item) {
        return reservationItemRepository.save(item);
    }
    public String createReservationData() {
        return UUID.randomUUID().toString();
    }

    public long getReservationCount(Product product, LocalDate travelDate) {
        LocalDateTime from = travelDate.atTime(0,0,0);
        LocalDateTime to = travelDate.atTime(23,59,59);
        long result = reservationItemRepository.findByProductIdAndTravelDateBetween(product.getId(), from, to).size();
        log.debug("Reservations for product {} betweeb date {} - {} is {}", product, from, to, result);
        return result;
    }

    private boolean isValidReservation(ReservationItem item,
                                       ReservationRequestReservations reservation) {
        if(item == null || item.getReservationValidTo() == null) {
            log.info("Did not find reservation with reservation data {}",
                    reservation.getReservationData());
            return false;
        }

        if (!item.getReservationValidTo().isAfter(OffsetDateTime.now())) {
            log.info("Reservation {} has expired",
                    reservation.getReservationData() );
            return false;
        }
        return true;
    }


    public ReservationItem createResevartionItem(Product product,
                                          Reservation reservation, Travel travel) {
        ReservationItem item = new ReservationItem();
        item.setConfirmed(false);
        item.setReservationData(createReservationData());
        item.setProductId(product.getId());
        item.setTravelDate(travel.getDateTime().toLocalDate().atTime(12,00));
        item.setReservationValidTo(OffsetDateTime.now().plusMinutes(
                Constants.RESERVATION_AVAILABILITY_MINUTES));
        item.setCaseId(reservation.getCaseId());
        item.setClientId(reservation.getClientId());
        //item.setPassengerCategory(category);
        item.setValidFrom(timetableService.getProductDeparture(
                travel.getDateTime().toLocalDate(), product));
        item.setValidTo(item.getValidFrom());
        return item;
    }
}
