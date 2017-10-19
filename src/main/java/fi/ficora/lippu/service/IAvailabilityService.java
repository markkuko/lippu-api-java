package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelPassenger;

import java.time.LocalDate;

/**
 * Interface declaration for Availability functionality.
 *
 * @author markkuko
 */
public interface IAvailabilityService {


    /**
     * Checks if there are available quota for given request and
     * then reserve it if there is quota available.
     * @param reservation Reservation defailt to which new item is added.
     * @param product Product to which reservation is added.
     * @param passenger Passenger and extra requirements information
     *                  for the avialability.
     * @param travel Information about the travel.
     * @return Reserved item.
     */
    ReservationItem addAvailability(Reservation reservation,
                                    Product product,
                                    TravelPassenger passenger,
                                    Travel travel);

    /**
     * Checks if there are available capacity for given product,
     * in the given date with amount of passengers.
     * @param product Product for the capacity check
     * @param travelDate Date for the travel.
     * @param passengers Number of passengers.
     * @return Reservation for the capacity reservation if necessary
     * capacity is available, otherwice null.
     */
    Reservation checkForCapacity(Product product, LocalDate travelDate,
                                 int passengers);
}
