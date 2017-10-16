package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.time.DayOfWeek;
import java.util.List;

public class Timetable {
    @Id
    private String id;
    private String productId;
    private  List<DayOfWeek> operatedOn;
    private int hour;
    private int minute;

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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
