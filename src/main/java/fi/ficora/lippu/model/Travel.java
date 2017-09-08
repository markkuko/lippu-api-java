package fi.ficora.lippu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Travel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-05T15:09:31.417+03:00")

public class Travel   {
  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("dateTime")
  private OffsetDateTime dateTime = null;

  public Travel productType(String productType) {
    this.productType = productType;
    return this;
  }

   /**
   * Availability/Product kyselyn vastauksena tulleen tuotteen tunnus.
   * @return productType
  **/
  @ApiModelProperty(required = true, value = "Availability/Product kyselyn vastauksena tulleen tuotteen tunnus.")
  @NotNull


  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Travel dateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

   /**
   * Matkan ajankohta.
   * @return dateTime
  **/
  @ApiModelProperty(required = true, value = "Matkan ajankohta.")
  @NotNull

  @Valid

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Travel travel = (Travel) o;
    return Objects.equals(this.productType, travel.productType) &&
        Objects.equals(this.dateTime, travel.dateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, dateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Travel {\n");
    
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
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

