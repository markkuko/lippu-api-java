package fi.ficora.lippu.service;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.model.ReservationRequest;

/**
 * Interface declaration for Reservation functionality.
 */
public interface IReservationService {


    /**
     * Removes reservation from repository.
     * @param caseId Reservation caseId
     * @return Operation result code
     * @see @{@link Constants}.
     */
    public int delete(String caseId);

    /**
     * Creates reservation for availability search.
     * @param request
     * @return
     */
    public Reservation add(ReservationRequest request);
}
