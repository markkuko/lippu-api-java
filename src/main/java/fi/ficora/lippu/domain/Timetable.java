package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.time.DayOfWeek;
import java.util.List;

public class Timetable {
    @Id
    private String id;
    private String productId;
    private  List<DayOfWeek> operatedOn;
    private int departureHour;
    private int departureMinute;
    private int arrivalHour;
    private int arrivalMinute;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<DayOfWeek> getOperatedOn() {
        return operatedOn;
    }

    public void setOperatedOn( List<DayOfWeek> operatedOn) {
        this.operatedOn = operatedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(int departureHour) {
        this.departureHour = departureHour;
    }

    public int getDepartureMinute() {
        return departureMinute;
    }

    public void setDepartureMinute(int departureMinute) {
        this.departureMinute = departureMinute;
    }

    public int getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(int arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public int getArrivalMinute() {
        return arrivalMinute;
    }

    public void setArrivalMinute(int arrivalMinute) {
        this.arrivalMinute = arrivalMinute;
    }
}
