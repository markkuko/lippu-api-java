package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * Response for authentication initialization.
 */
@ApiModel(description = "Response for authentication initialization.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

public class AuthenticationInitResponse   {
  @JsonProperty("nonce")
  private String nonce = null;

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationInitResponse authenticationInitResponse = (AuthenticationInitResponse) o;
    return Objects.equals(this.nonce, authenticationInitResponse.nonce);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nonce);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationInitResponse {\n");
    
    sb.append("    nonce: ").append(toIndentedString(nonce)).append("\n");
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

