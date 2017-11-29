package fi.ficora.lippu.util;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.*;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.*;
import fi.ficora.lippu.domain.model.Transport;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .accessibility(accessibilityListToApi(product.getAccessibilities()))
                .extraServices(extraServiceListToApi(product.getExtraServiceFeatures()))
                .suitablePassengerCategories(product.getSuitablePassengerCategories());

    }

    public static TravelAvailability reservationItemToTravelAvailability(
                ReservationItem item, TravelPassenger passenger,
                Map<String, AccessibilityFeature> accessibilities,
                Map<String, ExtraServiceFeature> services) {
        TravelPassengerReservation returnPassenger =
                new TravelPassengerReservation()
                .category(item.getPassengerCategory());
        returnPassenger.setAccessibility(
                accessibilityListToApiReservation(accessibilities));
        if(services.size() > 0) {
            returnPassenger.setExtraServices(
                    extraServiceListToApiReservation(services));
        }
        return new TravelAvailability()
                .travelEntitlementId(item.getTravelEntitlementId())
                .addApplicableForPassengersItem(returnPassenger)
                .validTo(item.getReservationValidTo());
    }

    public static TravelEntitlement reservationItemToTravelEntitlement(
            ReservationItem item) {
        return new TravelEntitlement()
                .travelEntitlementId(item.getTravelEntitlementId())
                .ticketPayload(item.getTicketPayload())
                .ticketType(item.getTicketType())
                .activated(item.isActivated())
                .caseId(item.getCaseId())
                .validFrom(item.getValidFrom())
                .validTo(item.getValidTo())
                .accessibility(accessibilityListToApiReservation(
                        item.getAccessibilities()))
                .extraServices(extraServiceListToApiReservation(
                        item.getExtraServiceFeatures()));
    }
    public static ProductFare fareToProductFare(Fare fare) {
        if(fare == null ) {
            return null;
        }
        ProductFare productFare = new ProductFare();
        productFare.setCurrency(fare.getCurrency());
        if(fare.getVatPercent() != null) {
            productFare.setVatPercent(BigDecimal.valueOf(fare.getVatPercent()));
        }
        productFare.setAmount(fare.getAmount());
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
                .travelEntitlementId(item.getTravelEntitlementId())
                .ticketPayload(item.getTicketPayload())
                .validFrom(item.getValidFrom())
                .validTo(item.getValidTo().plusMinutes(Constants.TICKET_VALID_PERIOD_IN_MINUTES));
    }

    public static Accessibility accessibilityToApi(AccessibilityFeature a) {
        if(a == null) {
            return null;
        }
        return new Accessibility()
                .title(Accessibility.TitleEnum.fromValue(a.getTitle().toString()))
                .additionalInformation(a.getAdditionalInformation())
                .description(a.getDescription())
                .fare(fareToProductFare(a.getFare()));
    }

    public static AccessibilityReservation accessibilityReservationToApi(AccessibilityFeature a) {
        if(a == null) {
            return null;
        }
        return new AccessibilityReservation()
                .title(AccessibilityReservation.TitleEnum.fromValue(a.getTitle().toString()))
                .additionalInformation(a.getAdditionalInformation())
                .description(a.getDescription())
                .fare(fareToProductFare(a.getFare()))
                .accessibilityReservationId(a.getAccessibilityReservationId());
    }
    public static ExtraService extraServiceToApi(ExtraServiceFeature e) {
        if(e == null) {
            return null;
        }
        return (ExtraService) new ExtraService()
                .description(e.getDescription())
                .fare(fareToProductFare(e.getFare()))
                .title(e.getTitle());

    }

    public static ExtraServiceReservation extraServiceToApiReservation(ExtraServiceFeature e) {
        if(e == null) {
            return null;
        }
        return new ExtraServiceReservation()
                .title(e.getTitle())
                .extraServiceReservationId(e.getExtraServiceReservationId())
                .description(e.getDescription())
                .fare(fareToProductFare(e.getFare()));
    }

    public static List<ExtraService> extraServiceListToApi(Map<String, ExtraServiceFeature> list) {
        if(list == null ) {
            return null;
        }

        return list.values().stream().map(e -> extraServiceToApi(e))
                .collect(Collectors.toList());
    }

    public static List<ExtraServiceReservation>
        extraServiceListToApiReservation(Map<String, ExtraServiceFeature> list) {
        if(list == null ) {
            return null;
        }
        return list.values().stream().map(e -> extraServiceToApiReservation(e))
                .collect(Collectors.toList());
    }
    public static List<AccessibilityReservation> accessibilityListToApiReservation(
            Map<String, AccessibilityFeature> list) {
        if(list == null ) {
            return null;
        }
        return list.values().stream().map(a -> accessibilityReservationToApi(a))
                .collect(Collectors.toList());
    }
    public static List<Accessibility> accessibilityListToApi(
            Map<String, AccessibilityFeature> list) {
        if(list == null ) {
            return null;
        }
        return list.values().stream().map(a -> accessibilityToApi(a))
                .collect(Collectors.toList());
    }
    public static TravelResponse travelRequestToResponse(TravelRequest travel) {
        return new TravelResponse()
                .from(travel.getFrom())
                .to(travel.getTo())
                .productType(travel.getProductType())
                .serviceId(travel.getServiceId())
                .departureTime(travel.getDepartureTimeEarliest());
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
