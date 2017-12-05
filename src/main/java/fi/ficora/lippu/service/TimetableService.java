package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Timetable;
import fi.ficora.lippu.exception.TimetableNotFoundException;
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
 * Services for transport timetable operations.
 * @author markkuko
 */
@Service
public class TimetableService implements ITimetableService{

    @Autowired
    private TimetableRepository timetableRepository;

    private static final Logger log = LoggerFactory.getLogger(TimetableService.class);

    /**
     * Computes does the product given as a parameters operate
     * on the given  day.
     * @param date Search products based on this date
     * @param product Product to be searched.
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public boolean doesProductOperateOn(LocalDate date, Product product) {
        Timetable timetable = timetableRepository.findByProductId(
                product.getId());
        return timetable.getOperatedOn().contains(date.getDayOfWeek());

    }
    /**
     * Search if the product has departures on the day
     * after the given time.
     * @param departure Wanted departure time and departure.
     * @param product Product to be searched.
     * @return Boolean value, true if the product operates on the given day
     * and has departures past the time.
     */
    public boolean hasProductDepartures(OffsetDateTime departure,
                                        OffsetDateTime arrival,
                                        Product product)
            throws TimetableNotFoundException{
        Timetable timetable = timetableRepository.findByProductId(
                product.getId());
        if(timetable == null ) {
            throw new TimetableNotFoundException("Timetable not found");
        }
        if(departure == null && arrival != null) {
            if(timetable.getOperatedOn().contains(arrival.getDayOfWeek())) {
                if(timetable.getArrivalHour() < arrival.getHour()) {
                    return true;
                }else if(timetable.getArrivalHour() == arrival.getHour()
                        && timetable.getArrivalMinute() < arrival.getMinute()) {
                    return true;
                } else {
                    log.debug("Product {} does not have suitable arrivals after {}:{}"
                            ,product.getId()
                            ,arrival.getHour()
                            ,arrival.getMinute());
                    return false;
                }
            } else {
                log.debug("Product {} does not operate on {}",
                        product.getId());
                return false;
            }
        }
        if(timetable.getOperatedOn().contains(departure.getDayOfWeek())) {
            if(timetable.getDepartureHour() > departure.getHour()) {
                return true;
            }else if(timetable.getDepartureHour() == departure.getHour()
                && timetable.getDepartureMinute() > departure.getMinute()) {
                return true;
            } else {
                log.debug("Product {} does not have suitable departures after {}:{}"
                        ,product.getId()
                        ,departure.getHour()
                        ,departure.getMinute());
                return false;
            }
        } else {
            log.debug("Product {} does not operate on {}"
                    ,product.getId()
                    ,departure.getDayOfWeek());
            return false;
        }

    }
    /**
     * Computes the departure time for the product on the given day
     * @param date Date of the departure.
     * @param product Product for the
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public OffsetDateTime getProductDeparture(LocalDate date, Product product) {
        Timetable timetable = timetableRepository.findByProductId(
                product.getId());
        if(timetable == null) {
            return null;
        }
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return OffsetDateTime.of(date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                timetable.getDepartureHour(),
                timetable.getDepartureMinute(),
                0,
                0,
                offset
                );

    }
    /**
     * Computes the arrival time for the product on the given day
     * @param date Date of the arrival.
     * @param product Product for the
     * @return Boolean value, true if the product operates on the given day
     * and false otherwise.
     */
    public OffsetDateTime getProductArrival(LocalDate date, Product product) {
        Timetable timetable = timetableRepository.findByProductId(
                product.getId());
        if(timetable == null) {
            return null;
        }
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return OffsetDateTime.of(date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                timetable.getArrivalHour(),
                timetable.getArrivalMinute(),
                0,
                0,
                offset
        );

    }
    /**
     * Computes does which products have departures on the
     * given week day.
     * @param day A week day to get departures
     * @return List of product ids which have departures on the day
     */
    public List<String> getProductIdsOperateOnDay(DayOfWeek day) {
        List<String> products  = new ArrayList<>();
        for (Timetable timetable : timetableRepository.findAll()) {
            if(timetable.getOperatedOn().contains(day)) {
                products.add(timetable.getProductId());
            }
        }
        return products;
    }
}
