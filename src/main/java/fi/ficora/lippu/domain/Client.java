package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

public class Client {



    @Id
    private String account;
    private String keyfile;
    private String name;
    private String contract;
    private String pubKeyId;
    private String secret;

    public String getKeyfile() {
        return keyfile;
    }

    public Client setKeyfile(String keyfile) {

        this.keyfile = keyfile;
        return this;
    }



    public String getName() {
        return name;

    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getContract() {
        return contract;
    }

    public Client setContract(String contract) {
        this.contract = contract;
        return this;
    }

    public String getPubKeyId() {
        return pubKeyId;
    }

    public Client setPubKeyId(String pubKeyId) {
        this.pubKeyId = pubKeyId;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public Client setSecret(String secret) {
        this.secret = secret;
        return this;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}


