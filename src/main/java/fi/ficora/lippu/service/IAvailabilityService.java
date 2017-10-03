package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.AvailabilityRequest;
import fi.ficora.lippu.domain.model.AvailabilityResponse;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelPassenger;
import fi.ficora.lippu.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Interface declaration for Availability functionality.
 *
 * @author markkuko
 */
public interface IAvailabilityService {


    /**
     * Checks if there are available quota for given request and
     * then reservers it if there is quota available
     * @param reservation
     * @param product
     * @param passenger
     * @return
     */
    public ReservationItem addAvailability(Reservation reservation,
                                           Product product,
                                           TravelPassenger passenger,
                                           Travel travel);

    /**
     * Checks if there are available capasity for given product,
     * in the given date with amount of passengers
     * @param product
     * @param travelDate
     * @param passengers
     * @return
     */
    public Reservation checkForCapasity(Product product, LocalDate travelDate, int passengers);
}
