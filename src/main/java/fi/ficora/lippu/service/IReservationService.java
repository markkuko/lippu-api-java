package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.ExtraServiceFeature;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.exception.NotAuthorizedException;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface declaration for Reservation functionality. Methods
 * to create and delete reservation and reservation codes.
 */
public interface IReservationService {


    /**
     * Removes reservation from repository.
     * @param caseId Reservation caseId
     * @return Operation result code
     */
    int delete(String caseId) throws NotAuthorizedException;

    /**
     * Confirms previous reservation made with availability
     * query.
     * @return List of reservation items, that have been confirmed.
     */
    List<ReservationItem> confirmReservation(List<ReservationRequestReservations>
                                                     reservations);
    /**
     * Creates new empty reservation.
     *
     * @return Created reservation.
     */
    Reservation create();

    /**
     * Counts reservations for given product on the given day.
     * @param product Count reservations for this product.
     * @param travelDate The date where
     * @return Count how many confirmed
     */
    long getReservationCount(Product product, LocalDate travelDate);

    /**
     * Creates new a {@link ReservationItem}
     * @param product The product which reservation item is destined to use.
     * @param reservation The reservation this reservation item is linked to.
     * @param travel The travel information for the reservation item.
     * @param passenger The passenger category, extra services and accessibility
     *                  services for the passenger.
     * @return Count how many confirmed
     */

    ReservationItem createReservationItem(Product product,
                                          Reservation reservation,
                                          Travel travel,
                                          TravelPassenger passenger);

    /**
     * Add the given {@link ReservationItem} to the database.
     * @param item The reservation item to save.
     * @return The saved reservation item.
     */
    ReservationItem addReservationItem(ReservationItem item);

    /**
     * Generates reservation code for extra service to be
     * included in the reservation item.
     * @see {@link ReservationItem}
     * @param service The service to generate the reservation code for.
     * @return String representation for the extra service reservation code.
     */
    String generateExtraServiceReservationCode(ExtraServiceFeature service);
}
