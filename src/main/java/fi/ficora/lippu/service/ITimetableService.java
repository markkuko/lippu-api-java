package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Timetable;
import fi.ficora.lippu.repository.TimetableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for transport timetable operations service..
 * @author markkuko
 */

public interface ITimetableService {

    /**
     * Computes does the product given as a parameters operate
     * on the given  day.
     * @param date
     * @param product Product to
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public boolean doesProductOperateOn(LocalDate date, Product product);
    /**
     * Computes does the product given as a parameters operate
     * on the given day and is the
     * @param date
     * @param product Product to
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public boolean hasProductDepartures(OffsetDateTime date, Product product);

    /**
     * Computes the departure time for the product on the given day
     * @param date
     * @param product P
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public OffsetDateTime getProductDeparture(LocalDate date, Product product);
    /**
     * Computes does which products have departures on the
     * given week day.
     * @param day A week day to get departures
     * @return List of product ids which have departures on the day
     */
    public List<String> getProductIdsOperateOnDay(DayOfWeek day);
}
