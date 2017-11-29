package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ReservationItem {

    @Id
    private String id;
    private String travelEntitlementId;
    private String ticketPayload;
    private String ticketType;
    private OffsetDateTime validFrom;
    private OffsetDateTime validTo;
    private List<CustomerInfo> customerInfo;
    private List<String> chosenExtraReservationData;
    private Map<String, ExtraServiceFeature> extraServiceFeatures;
    private Map<String, AccessibilityFeature> accessibilities;
    private boolean confirmed;
    private boolean activated;
    private String productId;
    private String caseId;
    private String clientId;
    private LocalDateTime travelDate;
    private String passengerCategory;
    private OffsetDateTime reservationValidTo;

    public String getTravelEntitlementId() {
        return travelEntitlementId;
    }

    public ReservationItem setTravelEntitlementId(String travelEntitlementId) {
        this.travelEntitlementId = travelEntitlementId;
        return this;
    }

    public String getTicketPayload() {
        return ticketPayload;
    }

    public ReservationItem setTicketPayload(String ticketPayload) {
        this.ticketPayload = ticketPayload;
        return this;
    }

    public OffsetDateTime getValidFrom() {
        return validFrom;
    }

    public ReservationItem setValidFrom(OffsetDateTime validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public OffsetDateTime getValidTo() {
        return validTo;
    }

    public ReservationItem setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
        return this;
    }

    public List<CustomerInfo> getCustomerInfo() {
        return customerInfo;
    }

    public ReservationItem setCustomerInfo(List<CustomerInfo> customerInfo) {
        this.customerInfo = customerInfo;
        return this;
    }

    public List<String> getChosenExtraReservationData() {
        return chosenExtraReservationData;
    }

    public ReservationItem setChosenExtraReservationData(List<String> chosenExtraReservationData) {
        this.chosenExtraReservationData = chosenExtraReservationData;
        return this;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDateTime travelDate) {
        this.travelDate = travelDate;
    }

    public String getPassengerCategory() {
        return passengerCategory;
    }

    public void setPassengerCategory(String passengerCategory) {
        this.passengerCategory = passengerCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OffsetDateTime getReservationValidTo() {
        return reservationValidTo;
    }

    public void setReservationValidTo(OffsetDateTime reservationValidTo) {
        this.reservationValidTo = reservationValidTo;
    }

    public Map<String, ExtraServiceFeature> getExtraServiceFeatures() {
        return extraServiceFeatures;
    }

    public void setExtraServiceFeatures(Map<String, ExtraServiceFeature> extraServiceFeatures) {
        this.extraServiceFeatures = extraServiceFeatures;
    }

    public Map<String, AccessibilityFeature> getAccessibilities() {
        return accessibilities;
    }

    public void setAccessibilities(Map<String, AccessibilityFeature> accessibilities) {
        this.accessibilities = accessibilities;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
