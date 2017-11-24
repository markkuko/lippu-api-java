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
     * @param date Wanted departure time and date.
     * @param product Product to be searched.
     * @return Boolean value, true if the product operates on the given day
     * and has departures past the time.
     */
    public boolean hasProductDepartures(OffsetDateTime date, Product product) {
        if(date == null) {
            date = OffsetDateTime.now();
        }
        Timetable timetable = timetableRepository.findByProductId(
                product.getId());
        if(timetable != null &&
                timetable.getOperatedOn().contains(date.getDayOfWeek())) {
            if(timetable.getHour() > date.getHour()) {
                return true;
            }else if(timetable.getHour() == date.getHour()
                && timetable.getMinute() > date.getMinute()) {
                return true;
            } else {
                log.debug("Product {} does not have departures after {}:{}"
                        ,product.getId()
                        ,date.getHour()
                        ,date.getMinute());
                return false;
            }
        } else {
            log.debug("Product {} does not operate on {}"
                    ,product.getId()
                    ,date.getDayOfWeek());
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
        if(timetable != null) {
            ZoneOffset offset = OffsetDateTime.now().getOffset();
            return OffsetDateTime.of(date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    timetable.getHour(),
                    timetable.getMinute(),
                    0,
                    0,
                    offset
                    );

        } else {
            return null;
        }

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
