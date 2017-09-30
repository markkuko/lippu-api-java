package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AuthenticationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class AuthenticationRequest   {
  @JsonProperty("data")
  private String data = null;

  @JsonProperty("pubKeyId")
  private String pubKeyId = null;

  @JsonProperty("snonce")
  private String snonce = null;

  @JsonProperty("cnonce")
  private String cnonce = null;

  /**
   * The algorithm to sign message **data**.
   */
  public enum AlgEnum {
    RSA_SHA256("RSA+SHA256"),
    
    ECDSA("ECDSA");

    private String value;

    AlgEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AlgEnum fromValue(String text) {
      for (AlgEnum b : AlgEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("alg")
  private AlgEnum alg = null;

  public AuthenticationRequest data(String data) {
    this.data = data;
    return this;
  }

   /**
   * A cryptographically signed message. Compiled by merging the nonce + client nonce server into a single long string. The server has base64 encoded its own value. The client end can do the encoding they want as it is used as part of the verification process.
   * @return data
  **/
  @ApiModelProperty(required = true, value = "A cryptographically signed message. Compiled by merging the nonce + client nonce server into a single long string. The server has base64 encoded its own value. The client end can do the encoding they want as it is used as part of the verification process.")
  @NotNull


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public AuthenticationRequest pubKeyId(String pubKeyId) {
    this.pubKeyId = pubKeyId;
    return this;
  }

   /**
   * The server identifier for the Public Key
   * @return pubKeyId
  **/
  @ApiModelProperty(required = true, value = "The server identifier for the Public Key")
  @NotNull


  public String getPubKeyId() {
    return pubKeyId;
  }

  public void setPubKeyId(String pubKeyId) {
    this.pubKeyId = pubKeyId;
  }

  public AuthenticationRequest snonce(String snonce) {
    this.snonce = snonce;
    return this;
  }

   /**
   * The cryptographic nonce value created by server
   * @return snonce
  **/
  @ApiModelProperty(required = true, value = "The cryptographic nonce value created by server")
  @NotNull


  public String getSnonce() {
    return snonce;
  }

  public void setSnonce(String snonce) {
    this.snonce = snonce;
  }

  public AuthenticationRequest cnonce(String cnonce) {
    this.cnonce = cnonce;
    return this;
  }

   /**
   * The cryptographic nonce value created by client
   * @return cnonce
  **/
  @ApiModelProperty(required = true, value = "The cryptographic nonce value created by client")
  @NotNull


  public String getCnonce() {
    return cnonce;
  }

  public void setCnonce(String cnonce) {
    this.cnonce = cnonce;
  }

  public AuthenticationRequest alg(AlgEnum alg) {
    this.alg = alg;
    return this;
  }

   /**
   * The algorithm to sign message **data**.
   * @return alg
  **/
  @ApiModelProperty(required = true, value = "The algorithm to sign message **data**.")
  @NotNull


  public AlgEnum getAlg() {
    return alg;
  }

  public void setAlg(AlgEnum alg) {
    this.alg = alg;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationRequest authenticationRequest = (AuthenticationRequest) o;
    return Objects.equals(this.data, authenticationRequest.data) &&
        Objects.equals(this.pubKeyId, authenticationRequest.pubKeyId) &&
        Objects.equals(this.snonce, authenticationRequest.snonce) &&
        Objects.equals(this.cnonce, authenticationRequest.cnonce) &&
        Objects.equals(this.alg, authenticationRequest.alg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, pubKeyId, snonce, cnonce, alg);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationRequest {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    pubKeyId: ").append(toIndentedString(pubKeyId)).append("\n");
    sb.append("    snonce: ").append(toIndentedString(snonce)).append("\n");
    sb.append("    cnonce: ").append(toIndentedString(cnonce)).append("\n");
    sb.append("    alg: ").append(toIndentedString(alg)).append("\n");
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

