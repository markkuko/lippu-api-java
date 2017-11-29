package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Availability;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelAvailability;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.domain.model.TravelRequest;
import fi.ficora.lippu.exception.ProductNotFoundException;
import fi.ficora.lippu.exception.TimetableNotFoundException;

import java.time.LocalDate;
import java.util.List;

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
    TravelAvailability addAvailability(Reservation reservation,
                                       Product product,
                                       TravelPassenger passenger,
                                       TravelRequest travel);

    /**
     * Checks if there are available capacity for given product,
     * in the given date with amount of passengers.
     * @param product Product for the capacity check
     * @param travelDate Date for the travel.
     * @param passengers List of Travel Passengers
     * @return Reservation for the capacity reservation if necessary
     * capacity is available, otherwice null.
     */
    Reservation checkForCapacity(Product product, LocalDate travelDate,
                                 List<TravelPassenger> passengers);

    /**
     * Makes soft booking for the travels indicated
     * by the parameters. If
     * @param request Travel details: from, to, departure time,
     *                arrival time.
     * @param passengers List of passengers and their requirements for
     *                   extra services and accessibilities.
     * @param contract Search products within this contract
     * @return The availabilities and product if soft booking
     * succeeded.
     */
    Availability getSoftBookingAvailability(TravelRequest request,
                                            List<TravelPassenger> passengers,
                                            String contract)
        throws ProductNotFoundException,
            TimetableNotFoundException;
}