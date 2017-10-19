package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Response for authentication initialization.
 */
@ApiModel(description = "Response for authentication initialization.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class AuthenticationInitResponse   {
  @JsonProperty("nonce")
  private String nonce = null;

  @JsonProperty("user")
  private String user = null;

  @JsonProperty("expires")
  private OffsetDateTime expires = null;

  public AuthenticationInitResponse nonce(String nonce) {
    this.nonce = nonce;
    return this;
  }

   /**
   * Server side nonce value.
   * @return nonce
  **/
  @ApiModelProperty(required = true, value = "Server side nonce value.")
  @NotNull


  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public AuthenticationInitResponse user(String user) {
    this.user = user;
    return this;
  }

   /**
   * Account name for the client company.
   * @return user
  **/
  @ApiModelProperty(value = "Account name for the client company.")


  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public AuthenticationInitResponse expires(OffsetDateTime expires) {
    this.expires = expires;
    return this;
  }

   /**
   * Nonce expiration time.
   * @return expires
  **/
  @ApiModelProperty(required = true, value = "Nonce expiration time.")
  @NotNull

  @Valid

  public OffsetDateTime getExpires() {
    return expires;
  }

  public void setExpires(OffsetDateTime expires) {
    this.expires = expires;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationInitResponse authenticationInitResponse = (AuthenticationInitResponse) o;
    return Objects.equals(this.nonce, authenticationInitResponse.nonce) &&
        Objects.equals(this.user, authenticationInitResponse.user) &&
        Objects.equals(this.expires, authenticationInitResponse.expires);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nonce, user, expires);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationInitResponse {\n");
    
    sb.append("    nonce: ").append(toIndentedString(nonce)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    expires: ").append(toIndentedString(expires)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

