package fi.ficora.lippu.repository;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationProperties("data")
public class DataRepository {

    private String operator;
    private String secret;
    private String privateKey;
    private String publicKey;
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public List<String> getPassengerCategories() {
        return passengerCategories;
    }

    public void setPassengerCategories(List<String> passengerCategories) {
        this.passengerCategories = passengerCategories;
    }
}
