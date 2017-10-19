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
 * The authentication token for a successful authentication.
 */
@ApiModel(description = "The authentication token for a successful authentication.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class AuthenticationResponse   {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("user")
  private String user = null;

  @JsonProperty("expires")
  private OffsetDateTime expires = null;

  public AuthenticationResponse token(String token) {
    this.token = token;
    return this;
  }

   /**
   * JWT based authentication token
   * @return token
  **/
  @ApiModelProperty(required = true, value = "JWT based authentication token")
  @NotNull


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AuthenticationResponse user(String user) {
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

  public AuthenticationResponse expires(OffsetDateTime expires) {
    this.expires = expires;
    return this;
  }

   /**
   * Token expiration time.
   * @return expires
  **/
  @ApiModelProperty(required = true, value = "Token expiration time.")
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
    AuthenticationResponse authenticationResponse = (AuthenticationResponse) o;
    return Objects.equals(this.token, authenticationResponse.token) &&
        Objects.equals(this.user, authenticationResponse.user) &&
        Objects.equals(this.expires, authenticationResponse.expires);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user, expires);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationResponse {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

