package fi.ficora.lippu.domain;


public class AccessibilityFeature {

    public enum TitleEnum {
        WHEELCHAIR("WHEELCHAIR"),

        PUSHCHAIR("PUSHCHAIR"),

        PASSENGER_CART("PASSENGER_CART"),

        LOW_FLOOR("LOW-FLOOR"),

        GUIDE_DOG("GUIDE_DOG"),

        ONBOARD_ASSISTANCE("ONBOARD_ASSISTANCE"),

        BOARDING_ASSISTANCE("BOARDING_ASSISTANCE"),

        UNACCOMPANIED_MINOR_ASSISTANCE("UNACCOMPANIED_MINOR_ASSISTANCE"),

        OTHER("OTHER"),

        STEP_FREE_ACCESS("STEP_FREE_ACCESS"),

        UNKNOWN("UNKNOWN");

        private String value;

        TitleEnum(String value) {
            this.value = value;
        }
        public String toString() {
            return String.valueOf(value);
        }
    }
    private AccessibilityFeature.TitleEnum title;

    private String additionalInformation;

    private String description;

    private Fare fare;

    private String accessibilityReservationId;

    public TitleEnum getTitle() {
        return title;
    }

    public void setTitle(TitleEnum title) {
        this.title = title;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public String getAccessibilityReservationId() {
        return accessibilityReservationId;
    }

    public void setAccessibilityReservationId(String accessibilityReservationId) {
        this.accessibilityReservationId = accessibilityReservationId;
    }
}
