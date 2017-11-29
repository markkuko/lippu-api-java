package fi.ficora.lippu.service;

import fi.ficora.lippu.controller.NotFoundException;
import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.domain.model.TravelRequest;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface declaration for Reservation functionality. Methods
 * to create and delete reservation and reservation codes.
 */
public interface IReservationService {


    /**
     * Removes reservation from repository.
     * @param caseId Reservation caseId, the reservation to be removed.
     * @return Operation result code. See {@link fi.ficora.lippu.config.Constants}.
     */
    int delete(String caseId) throws NotAuthorizedException,
            ResourceNotFoundException;

    /**
     * Removes one reservation item. If its the only item,
     * deletes also the reservation.
     * @param travelEntitlementId The id for travel entitlement.
     * @return Operation result code. See {@link fi.ficora.lippu.config.Constants}.
     */
    int deleteReservationItem(String travelEntitlementId) throws
            NotAuthorizedException, ResourceNotFoundException;

    /**
     * Activate reservation item, identified by travel entitlement id.
     * @param travelEntitlementId The id for travel entitlement.
     * @return Reservation item after the activation.
     */
    ReservationItem activateReservationItem(String travelEntitlementId)
            throws NotAuthorizedException, ResourceNotFoundException;
    /**
     * Gets one reservation item from repository. Throws
     * {@link NotAuthorizedException} if client is not authorized
     * to for the reservationItem. Throws {@link NotFoundException}
     * if the reservation item is not found.
     * @param travelEntitlementId The id for travel entitlement.
     * @return Operation result code.
     */
    ReservationItem getReservationItem(String travelEntitlementId)
            throws NotAuthorizedException, ResourceNotFoundException;

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
                                          TravelRequest travel,
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

    /**
     * Generates reservation code for accessibility feature to be
     * included in the reservation item.
     * @see {@link ReservationItem}
     * @param service The service to generate the reservation code for.
     * @return String representation for the accessibility feature reservation code.
     */
    String generateAccessiblityReservationCode(AccessibilityFeature service);
}
