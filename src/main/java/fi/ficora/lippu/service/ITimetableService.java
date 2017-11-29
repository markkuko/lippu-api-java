package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.exception.TimetableNotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Interface for transport timetable operations service.
 * Current design decision is that one product can have only
 * one departure per day.
 * @author markkuko
 */

public interface ITimetableService {

    /**
     * Computes does the product given as a parameters operate
     * on the given  day.
     * @param date The date to search products departures.
     * @param product The product for which departures are searched.
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    boolean doesProductOperateOn(LocalDate date, Product product);
    /**
     * Computes does the product given as a parameters operate
     * on the given day and is the
     * @param departure The departure time to search suitable departures.
     * @param arrival The arrival time to search suitable departures.
     * @param product The product for which departures are searched.
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    boolean hasProductDepartures(OffsetDateTime departure,
                                 OffsetDateTime arrival,
                                 Product product)
            throws TimetableNotFoundException;

    /**
     * Computes the departure time for the product on the given day
     * @param date The date when the
     * @param product The product for which departure time is searched.
     * @return OffsetDateTime departure value for the products on the date.
     */
    OffsetDateTime getProductDeparture(LocalDate date, Product product);
    /**
     * Computes the arrival time for the product on the given day.
     * @param date The date when the arrival is.
     * @param product The product for which departure time is searched.
     * @return OffsetDateTime departure value for the products on the date.
     */
    OffsetDateTime getProductArrival(LocalDate date, Product product);
    /**
     * Computes which products have departures on the
     * given week day.
     * @param day A week day to get departures
     * @return List of product ids which have departures on the day
     */
    List<String> getProductIdsOperateOnDay(DayOfWeek day);
}
