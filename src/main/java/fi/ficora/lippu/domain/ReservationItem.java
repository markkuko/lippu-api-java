package fi.ficora.lippu.domain;

import fi.ficora.lippu.domain.model.CustomerInfo;

import java.time.OffsetDateTime;
import java.util.List;

public class ReservationItem {
    private String reservationData;
    private String ticketPayload;
    private OffsetDateTime validFrom;
    private OffsetDateTime validTo;
    private List<CustomerInfo> customerInfo;
    private List<String> chosenExtraReservationDatas;

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
}
