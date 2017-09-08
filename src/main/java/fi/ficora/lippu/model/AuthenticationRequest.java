package fi.ficora.lippu.model;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-05T15:09:31.417+03:00")

public class AuthenticationRequest   {
  @JsonProperty("data")
  private String data = null;

  @JsonProperty("pubKeyId")
  private String pubKeyId = null;

  @JsonProperty("cnonce")
  private String cnonce = null;

  /**
   * Viestin **data** elementin signaukseen käytetty algoritmi
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
   * Kryptografisesti signattu viesti. Koostetaan yhdistämällä palvelimen nonce + client nonce yhdeksi pitkäksi merkkijonoksi. Palvelin on base64-enkoodannut oman arvonsa. Asiakaspää voi tehdä haluamansa enkoodauksen sillä sitä käytetään sellaisenaan osana verifiontiprosessia.
   * @return data
  **/
  @ApiModelProperty(required = true, value = "Kryptografisesti signattu viesti. Koostetaan yhdistämällä palvelimen nonce + client nonce yhdeksi pitkäksi merkkijonoksi. Palvelin on base64-enkoodannut oman arvonsa. Asiakaspää voi tehdä haluamansa enkoodauksen sillä sitä käytetään sellaisenaan osana verifiontiprosessia.")
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
   * Signauksen verifiointiin käytettävän julkisen avaimen palvelintunniste
   * @return pubKeyId
  **/
  @ApiModelProperty(required = true, value = "Signauksen verifiointiin käytettävän julkisen avaimen palvelintunniste")
  @NotNull


  public String getPubKeyId() {
    return pubKeyId;
  }

  public void setPubKeyId(String pubKeyId) {
    this.pubKeyId = pubKeyId;
  }

  public AuthenticationRequest cnonce(String cnonce) {
    this.cnonce = cnonce;
    return this;
  }

   /**
   * Asiakaspään luoma kryptografinen nonce-arvo muunnettuna merkkijonoksi
   * @return cnonce
  **/
  @ApiModelProperty(required = true, value = "Asiakaspään luoma kryptografinen nonce-arvo muunnettuna merkkijonoksi")
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
   * Viestin **data** elementin signaukseen käytetty algoritmi
   * @return alg
  **/
  @ApiModelProperty(required = true, value = "Viestin **data** elementin signaukseen käytetty algoritmi")
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
        Objects.equals(this.cnonce, authenticationRequest.cnonce) &&
        Objects.equals(this.alg, authenticationRequest.alg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, pubKeyId, cnonce, alg);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationRequest {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    pubKeyId: ").append(toIndentedString(pubKeyId)).append("\n");
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

