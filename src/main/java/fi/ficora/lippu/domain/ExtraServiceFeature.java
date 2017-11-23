package fi.ficora.lippu.domain;

public class ExtraServiceFeature {

    private String title;
    private String description;
    private String extraServiceReservationId;
    private Fare fare;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraServiceReservationId() {
        return extraServiceReservationId;
    }

    public void setExtraServiceReservationId(String extraServiceReservationId) {
        this.extraServiceReservationId = extraServiceReservationId;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
