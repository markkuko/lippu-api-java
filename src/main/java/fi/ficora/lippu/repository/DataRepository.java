package fi.ficora.lippu.repository;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import fi.ficora.lippu.domain.Client;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationProperties("data")
public class DataRepository {

    private String operator;
    private String secret;
    private String privatekey;
    private String publickey;
    private List<String> passengerCategories;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public List<String> getPassengerCategories() {
        return passengerCategories;
    }

    public void setPassengerCategories(List<String> passengerCategories) {
        this.passengerCategories = passengerCategories;
    }
}
