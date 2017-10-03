package fi.ficora.lippu.util;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Fare;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ConversionUtil {

    public static ProductDescription productToProductDescription(Product product,
                                                                 OffsetDateTime validFrom) {
        ProductDescription productDescription = new ProductDescription()
                .description(product.getDescription())
                .contract(product.getContract())
                .name(product.getName())
                .validFrom(validFrom)
                .validTo(validFrom.plusMinutes(Constants.TICKET_VALID_PERIOD_IN_MINUTES))
                .accessibility(product.getAccessibilities())
                .suitablePassengerCategories(product.getSuitablePassengerCategories());
        return productDescription;

    }

    public static TravelAvailability reservationItemToTravelPassenger(
            ReservationItem item) {
        TravelPassenger passenger = new TravelPassenger()
                .category(item.getPassengerCategory());
        TravelAvailability availability = new TravelAvailability()
                .reservationData(item.getReservationData())
                .addApplicableForPassengersItem(passenger)
                .validTo(item.getReservationValidTo());
        return availability;
    }
    public static ProductFare fareToProductFare(Fare fare) {
        ProductFare productFare = new ProductFare()
                .currency(fare.getCurrency())
                .vatPercent(new BigDecimal(fare.getVat()))
                .amount(fare.getAmount());
        return productFare;
    }

    public static Transport transportToModelTransport(fi.ficora.lippu.domain.Transport
                                                      transport) {
            return new Transport().operator(transport.getOperator()).
                    vehicleInfo(transport.getVehicleInfo());
    }

    public static ReservationResponseConfirmedReservations reservationItemToConfirmedReservations
            (ReservationItem item) {
        return new ReservationResponseConfirmedReservations()
                .reservationData(item.getReservationData())
                .ticketPayload(item.getTicketPayload())
                .validFrom(item.getValidFrom())
                .validTo(item.getValidTo().plusMinutes(Constants.TICKET_VALID_PERIOD_IN_MINUTES));
    }
}
