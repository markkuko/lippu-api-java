package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class Product {


    @Id
    private String id;
    private String name;
    private String productType;
    private String contract;
    private String description;
    private Double fromLat;
    private Double fromLon;
    private Double toLat;
    private Double toLon;
    private String fromId;
    private String toId;
    private ArrayList<String> suitablePassengerCategories;
    private Map<String, AccessibilityFeature> accessibilities;
    private Map<String, ExtraServiceFeature> extraServiceFeatures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getSuitablePassengerCategories() {
        return suitablePassengerCategories;
    }

    public void setSuitablePassengerCategories(ArrayList<String> suitablePassengerCategories) {
        this.suitablePassengerCategories = suitablePassengerCategories;
    }

    public Map<String, AccessibilityFeature> getAccessibilities() {
        return accessibilities;
    }

    public void setAccessibilities(Map<String, AccessibilityFeature> accessibilities) {
        this.accessibilities = accessibilities;
    }

    public Map<String, ExtraServiceFeature> getExtraServiceFeatures() {
        return extraServiceFeatures;
    }

    public void setExtraServiceFeatures(Map<String, ExtraServiceFeature> extraServiceFeatures) {
        this.extraServiceFeatures = extraServiceFeatures;
    }

    public Double getFromLat() {
        return fromLat;
    }

    public void setFromLat(Double fromLat) {
        this.fromLat = fromLat;
    }

    public Double getFromLon() {
        return fromLon;
    }

    public void setFromLon(Double fromLon) {
        this.fromLon = fromLon;
    }

    public Double getToLat() {
        return toLat;
    }

    public void setToLat(Double toLat) {
        this.toLat = toLat;
    }

    public Double getToLon() {
        return toLon;
    }

    public void setToLon(Double toLon) {
        this.toLon = toLon;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    @Override
    public String toString() {
        return id;
    }
}
