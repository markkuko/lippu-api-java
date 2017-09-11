package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Product {


    @Id
    private String id;
    private String name;
    private String productType;
    private String contract;
    private String description;
    private ArrayList<String> suitablePassengerCategories;

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
}


