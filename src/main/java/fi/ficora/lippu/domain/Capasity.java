package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

public class Capasity {
    @Id
    private String id;
    private String productId;
    private int maxCapasity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getMaxCapasity() {
        return maxCapasity;
    }

    public void setMaxCapasity(int maxCapasity) {
        this.maxCapasity = maxCapasity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
