package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Reservation {

    @Id
    private String caseId;
    private List<ReservationItem> reservationItems;
    private String clientId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public List<ReservationItem> getReservationItems() {
        return reservationItems;
    }

    public void setReservationItems(List<ReservationItem> reservationItems) {
        this.reservationItems = reservationItems;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
