package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.CombinedLocation;
import fi.ficora.lippu.domain.model.Travel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Travel request.
 */
@ApiModel(description = "Travel request.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-24T14:36:56.606+02:00")

public class TravelRequest   {
  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("serviceId")
  private String serviceId = null;

  @JsonProperty("from")
  private CombinedLocation from = null;

  @JsonProperty("to")
  private CombinedLocation to = null;

  @JsonProperty("departureTimeEarliest")
  private OffsetDateTime departureTimeEarliest = null;

  @JsonProperty("arrivalTimeLatest")
  private OffsetDateTime arrivalTimeLatest = null;

  public TravelRequest productType(String productType) {
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

  public TravelRequest serviceId(String serviceId) {
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

  public TravelRequest from(CombinedLocation from) {
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

  public TravelRequest to(CombinedLocation to) {
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

  public TravelRequest departureTimeEarliest(OffsetDateTime departureTimeEarliest) {
    this.departureTimeEarliest = departureTimeEarliest;
    return this;
  }

   /**
   * The earliest datetime when customer wants to depart. If both, departureTimeEarliest and arrivalTimeLatest are submitted the departureTimeEarliest is used. If neither is submitted, the current datetime is used as departureTimeEarliest.
   * @return departureTimeEarliest
  **/
  @ApiModelProperty(value = "The earliest datetime when customer wants to depart. If both, departureTimeEarliest and arrivalTimeLatest are submitted the departureTimeEarliest is used. If neither is submitted, the current datetime is used as departureTimeEarliest.")

  @Valid

  public OffsetDateTime getDepartureTimeEarliest() {
    return departureTimeEarliest;
  }

  public void setDepartureTimeEarliest(OffsetDateTime departureTimeEarliest) {
    this.departureTimeEarliest = departureTimeEarliest;
  }

  public TravelRequest arrivalTimeLatest(OffsetDateTime arrivalTimeLatest) {
    this.arrivalTimeLatest = arrivalTimeLatest;
    return this;
  }

   /**
   * The latest datetime when customer wants to arrive to the destination. If both, departureTimeEarliest and arrivalTimeLatest are submitted the departureTimeEarliest is used. If neither is submitted, the current datetime is used as departureTimeEarliest.
   * @return arrivalTimeLatest
  **/
  @ApiModelProperty(value = "The latest datetime when customer wants to arrive to the destination. If both, departureTimeEarliest and arrivalTimeLatest are submitted the departureTimeEarliest is used. If neither is submitted, the current datetime is used as departureTimeEarliest.")

  @Valid

  public OffsetDateTime getArrivalTimeLatest() {
    return arrivalTimeLatest;
  }

  public void setArrivalTimeLatest(OffsetDateTime arrivalTimeLatest) {
    this.arrivalTimeLatest = arrivalTimeLatest;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TravelRequest travelRequest = (TravelRequest) o;
    return Objects.equals(this.productType, travelRequest.productType) &&
        Objects.equals(this.serviceId, travelRequest.serviceId) &&
        Objects.equals(this.from, travelRequest.from) &&
        Objects.equals(this.to, travelRequest.to) &&
        Objects.equals(this.departureTimeEarliest, travelRequest.departureTimeEarliest) &&
        Objects.equals(this.arrivalTimeLatest, travelRequest.arrivalTimeLatest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, serviceId, from, to, departureTimeEarliest, arrivalTimeLatest);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelRequest {\n");
    
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    departureTimeEarliest: ").append(toIndentedString(departureTimeEarliest)).append("\n");
    sb.append("    arrivalTimeLatest: ").append(toIndentedString(arrivalTimeLatest)).append("\n");
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

