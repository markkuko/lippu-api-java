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
                List<AccessibilityFeature> accessibilities,
                List<ExtraServiceFeature> services) {
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

    public static Accessibility accessibilityToApi(AccessibilityFeature accessibilityFeature) {
        if(accessibilityFeature == null) {
            return null;
        }
        return new Accessibility()
                .title(Accessibility.TitleEnum.fromValue(accessibilityFeature.getTitle().toString()))
                .additionalInformation(accessibilityFeature.getAdditionalInformation())
                .description(accessibilityFeature.getDescription())
                .fare(fareToProductFare(accessibilityFeature.getFare()));
    }

    public static AccessibilityReservation accessibilityReservationToApi(AccessibilityFeature accessibilityFeature) {
        if(accessibilityFeature == null) {
            return null;
        }
        return new AccessibilityReservation()
                .title(AccessibilityReservation.TitleEnum.fromValue(accessibilityFeature.getTitle().toString()))
                .additionalInformation(accessibilityFeature.getAdditionalInformation())
                .description(accessibilityFeature.getDescription())
                .fare(fareToProductFare(accessibilityFeature.getFare()))
                .accessibilityReservationId(accessibilityFeature.getAccessibilityReservationId());
    }
    public static ExtraService extraServiceToApi(ExtraServiceFeature extraServiceFeature) {
        if(extraServiceFeature == null) {
            return null;
        }
        return (ExtraService) new ExtraService()
                .description(extraServiceFeature.getDescription())
                .fare(fareToProductFare(extraServiceFeature.getFare()))
                .title(extraServiceFeature.getTitle());

    }

    public static ExtraServiceReservation extraServiceToApiReservation(ExtraServiceFeature extraServiceFeature) {
        if(extraServiceFeature == null) {
            return null;
        }
        return new ExtraServiceReservation()
                .title(extraServiceFeature.getTitle())
                .extraServiceReservationId(extraServiceFeature.getExtraServiceReservationId())
                .description(extraServiceFeature.getDescription())
                .fare(fareToProductFare(extraServiceFeature.getFare()));
    }

    public static List<ExtraService> extraServiceListToApi(List<ExtraServiceFeature> list) {
        if(list == null ) {
            return null;
        }
        List<ExtraService> extraServicesList
                = new ArrayList<>();
        for (ExtraServiceFeature service: list) {
            extraServicesList.add(extraServiceToApi(service));
        }
        return extraServicesList;
    }

    public static List<ExtraServiceReservation>
        extraServiceListToApiReservation(List<ExtraServiceFeature> list) {
        if(list == null ) {
            return null;
        }
        List<ExtraServiceReservation> extraServicesList
                = new ArrayList<>();
        for (ExtraServiceFeature service: list) {
            extraServicesList.add(extraServiceToApiReservation(service));
        }
        return extraServicesList;

    }
    public static List<AccessibilityReservation> accessibilityListToApiReservation(List<AccessibilityFeature> list) {
        if(list == null ) {
            return null;
        }
        List<AccessibilityReservation> accessibilityList = new ArrayList<>();
        for (AccessibilityFeature accessibilityFeature : list) {
            accessibilityList.add(accessibilityReservationToApi(accessibilityFeature));
        }
        return accessibilityList;
    }
    public static List<Accessibility> accessibilityListToApi(List<AccessibilityFeature> list) {
        if(list == null ) {
            return null;
        }
        List<Accessibility> accessibilityList = new ArrayList<>();
        for (AccessibilityFeature accessibilityFeature : list) {
            accessibilityList.add(accessibilityToApi(accessibilityFeature));
        }
        return accessibilityList;
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
