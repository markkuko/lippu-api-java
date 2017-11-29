package fi.ficora.lippu.domain;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.TravelAvailability;

import java.util.List;

public class Availability {
    private List<TravelAvailability> availabilityList;
    private Product product;

    public List<TravelAvailability> getAvailabilityList() {
        return availabilityList;
    }

    public void setAvailabilityList(List<TravelAvailability> availabilityList) {
        this.availabilityList = availabilityList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
