package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.CombinedLocation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Travel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-24T14:36:56.606+02:00")

public class Travel   {
  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("serviceId")
  private String serviceId = null;

  @JsonProperty("from")
  private CombinedLocation from = null;

  @JsonProperty("to")
  private CombinedLocation to = null;

  public Travel productType(String productType) {
    this.productType = productType;
    return this;
  }

   /**
   * Availability/Product identifier from the products query response.
   * @return productType
  **/
  @ApiModelProperty(required = true, value = "Availability/Product identifier from the products query response.")
  @NotNull


  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Travel serviceId(String serviceId) {
    this.serviceId = serviceId;
    return this;
  }

   /**
   * Identifier for specific transport operator service. With this value requestor can prefer certain timetable departure/service.
   * @return serviceId
  **/
  @ApiModelProperty(value = "Identifier for specific transport operator service. With this value requestor can prefer certain timetable departure/service.")


  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Travel from(CombinedLocation from) {
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CombinedLocation getFrom() {
    return from;
  }

  public void setFrom(CombinedLocation from) {
    this.from = from;
  }

  public Travel to(CombinedLocation to) {
    this.to = to;
    return this;
  }

   /**
   * Get to
   * @return to
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CombinedLocation getTo() {
    return to;
  }

  public void setTo(CombinedLocation to) {
    this.to = to;
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
        Objects.equals(this.serviceId, travel.serviceId) &&
        Objects.equals(this.from, travel.from) &&
        Objects.equals(this.to, travel.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, serviceId, from, to);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Travel {\n");
    
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
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

