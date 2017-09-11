package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.ReservationRequest;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public ReservationService() {

    }

    /**
     * Removes reservation from repository.
     * @param caseId Reservation caseId
     * @return Operation result code
     * @see @{@link Constants}.
     */
    public int delete(String caseId) {
        Reservation reservation = repository.findOne(caseId);
        if(reservation == null) {
            return Constants.RESULTCODE_NOT_FOUND;
        }
        // @todo Move authorization check to separate service.
        if (reservation.getClientId().equals(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            repository.delete(caseId);
            return Constants.RESULTCODE_SUCCESS;
        } else {
            return Constants.RESULTCODE_FORBIDDEN;
        }
    }

    /**
     * Creates reservation for availability search.
     * @param request
     * @return
     */
    public Reservation add(ReservationRequest request) {
        Reservation reservation = new Reservation();
        List<ReservationItem> reservationItems = new ArrayList<ReservationItem>();
        for (ReservationRequestReservations requestReservation:request.getReservations()) {
            ReservationItem item = new ReservationItem();
            // @todo Validate reservation data
            item.setReservationData(requestReservation.getReservationData());
            item.setCustomerInfo(requestReservation.getCustomerInfo());
            item.setChosenExtraReservationDatas(requestReservation.getChosenExtraReservationDatas());
            item.setTicketPayload(UUID.randomUUID().toString());
            item.setValidFrom(OffsetDateTime.now());
            item.setValidTo(OffsetDateTime.now());
            reservationItems.add(item);
        }
        reservation.setClientId((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reservation.setReservationItems(reservationItems);
        return repository.save(reservation);

    }

}
