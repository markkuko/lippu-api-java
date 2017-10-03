package fi.ficora.lippu.domain;

import fi.ficora.lippu.domain.model.CustomerInfo;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public class ReservationItem {

    @Id
    private String id;
    private String reservationData;
    private String ticketPayload;
    private OffsetDateTime validFrom;
    private OffsetDateTime validTo;
    private List<CustomerInfo> customerInfo;
    private List<String> chosenExtraReservationDatas;
    private boolean confirmed;
    private String productId;
    private String caseId;
    private String clientId;
    private LocalDateTime travelDate;
    private String passengerCategory;
    private OffsetDateTime reservationValidTo;

    public String getReservationData() {
        return reservationData;
    }

    public ReservationItem setReservationData(String reservationData) {
        this.reservationData = reservationData;
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

    public List<String> getChosenExtraReservationDatas() {
        return chosenExtraReservationDatas;
    }

    public ReservationItem setChosenExtraReservationDatas(List<String> chosenExtraReservationDatas) {
        this.chosenExtraReservationDatas = chosenExtraReservationDatas;
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

}
