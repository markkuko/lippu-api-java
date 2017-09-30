package fi.ficora.lippu.domain;

public class ClientKey {

    private String keyfile;
    private String pubKeyId;

    public String getKeyfile() {
        return keyfile;
    }

    public ClientKey setKeyfile(String keyfile) {

        this.keyfile = keyfile;
        return this;
    }

    public String getPubKeyId() {
        return pubKeyId;
    }

    public ClientKey setPubKeyId(String pubKeyId) {
        this.pubKeyId = pubKeyId;
        return this;
    }
}
