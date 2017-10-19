package fi.ficora.lippu.domain;

import java.time.OffsetDateTime;

/**
 * Class represents successful client authentication,
 * it has authentication token, token expire time
 * and the authenticated clientId.
 * @author markkuko
 */
public class Auth {
    private String token;
    private OffsetDateTime expires;
    private String clientId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getExpires() {
        return expires;
    }

    public void setExpires(OffsetDateTime expires) {
        this.expires = expires;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
