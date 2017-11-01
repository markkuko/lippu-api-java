package fi.ficora.lippu.util;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.Fare;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Utility class, which has general string methods and
 * static methods to convert classes to another class.
 */
public class ConversionUtil {

    public static ProductDescription productToProductDescription(Product product,
                                                                 OffsetDateTime validFrom) {
        return new ProductDescription()
                .description(product.getDescription())
                .contract(product.getContract())
                .productType(product.getProductType())
                .name(product.getName())
                .validFrom(validFrom)
                .validTo(validFrom.plusMinutes(Constants.TICKET_VALID_PERIOD_IN_MINUTES))
                .accessibility(product.getAccessibilities())
                .extraServices(product.getExtraServices())
                .suitablePassengerCategories(product.getSuitablePassengerCategories());

    }

    public static TravelAvailability reservationItemToTravelPassenger(
            ReservationItem item, TravelPassenger passenger,
                List<Accessibility> accessibilities,
                List<ExtraService> services) {
        TravelPassenger returnPassenger = new TravelPassenger()
                .category(item.getPassengerCategory());
        returnPassenger.setAccessibility(accessibilities);
        if(services.size() > 0) {
            returnPassenger.setExtraServices(services);
        }
        return new TravelAvailability()
                .reservationData(item.getReservationData())
                .addApplicableForPassengersItem(returnPassenger)
                .validTo(item.getReservationValidTo());
    }
    public static ProductFare fareToProductFare(Fare fare) {
        return new ProductFare()
                .currency(fare.getCurrency())
                .vatPercent(BigDecimal.valueOf(fare.getVat()))
                .amount(fare.getAmount());
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

    /**
     * Sanitizes string for logs, currently mitigates CRLF_INJECTION_LOGS type vulnerabilities.
     * @param message Message for log output.
     * @return Sanitized string from which \r \n are replaced.
     */
    public static String sanitizeLog(String message) {
        return message.replaceAll("[\r\n]","_");
    }
}
