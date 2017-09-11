package fi.ficora.lippu.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Nonce {
    @Id
    private String nonce;
    private LocalDateTime exp;
    private String client;

    public Nonce() {}
    public Nonce(String nonce) {
        this.nonce = nonce;
    }
    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
