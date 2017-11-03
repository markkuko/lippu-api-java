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
        TravelPassenger returnPassenger = new TravelPassenger()
                .category(item.getPassengerCategory());
        returnPassenger.setAccessibility(
                accessibilityListToApi(accessibilities));
        if(services.size() > 0) {
            returnPassenger.setExtraServices(extraServiceListToApi(services));
        }
        return new TravelAvailability()
                .reservationData(item.getReservationData())
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
                .reservationData(item.getReservationData())
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
    public static ExtraService extraServiceToApi(ExtraServiceFeature extraServiceFeature) {
        if(extraServiceFeature == null) {
            return null;
        }
        return new ExtraService()
                .title(extraServiceFeature.getTitle())
                .extraServiceReservationData(extraServiceFeature.getExtraServiceReservationData())
                .description(extraServiceFeature.getDescription())
                .fare(fareToProductFare(extraServiceFeature.getFare()));
    }

    public static List<ExtraService> extraServiceListToApi(List<ExtraServiceFeature> list) {
        if(list == null ) {
            return null;
        } else {
            List<fi.ficora.lippu.domain.model.ExtraService> extraServicesList
                    = new ArrayList<>();
            for (ExtraServiceFeature service: list) {
                extraServicesList.add(extraServiceToApi(service));
            }
            return extraServicesList;
        }
    }
    public static List<Accessibility> accessibilityListToApi(List<AccessibilityFeature> list) {
        if(list == null ) {
            return null;
        } else {
            List<Accessibility> accessibilityList = new ArrayList<>();
            for (AccessibilityFeature accessibilityFeature : list) {
                accessibilityList.add(accessibilityToApi(accessibilityFeature));
            }
            return accessibilityList;
        }
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
