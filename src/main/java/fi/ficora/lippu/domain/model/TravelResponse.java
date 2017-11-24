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
 * Travel response.
 */
@ApiModel(description = "Travel response.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-24T14:36:56.606+02:00")

public class TravelResponse   {
  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("serviceId")
  private String serviceId = null;

  @JsonProperty("from")
  private CombinedLocation from = null;

  @JsonProperty("to")
  private CombinedLocation to = null;

  @JsonProperty("departureTime")
  private OffsetDateTime departureTime = null;

  @JsonProperty("arrivalTime")
  private OffsetDateTime arrivalTime = null;

  public TravelResponse productType(String productType) {
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

  public TravelResponse serviceId(String serviceId) {
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

  public TravelResponse from(CombinedLocation from) {
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

  public TravelResponse to(CombinedLocation to) {
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

  public TravelResponse departureTime(OffsetDateTime departureTime) {
    this.departureTime = departureTime;
    return this;
  }

   /**
   * The datetime for the transportation departure.
   * @return departureTime
  **/
  @ApiModelProperty(value = "The datetime for the transportation departure.")

  @Valid

  public OffsetDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(OffsetDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public TravelResponse arrivalTime(OffsetDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
    return this;
  }

   /**
   * The datetime for the transportation arrival to the destination.
   * @return arrivalTime
  **/
  @ApiModelProperty(value = "The datetime for the transportation arrival to the destination.")

  @Valid

  public OffsetDateTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(OffsetDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TravelResponse travelResponse = (TravelResponse) o;
    return Objects.equals(this.productType, travelResponse.productType) &&
        Objects.equals(this.serviceId, travelResponse.serviceId) &&
        Objects.equals(this.from, travelResponse.from) &&
        Objects.equals(this.to, travelResponse.to) &&
        Objects.equals(this.departureTime, travelResponse.departureTime) &&
        Objects.equals(this.arrivalTime, travelResponse.arrivalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, serviceId, from, to, departureTime, arrivalTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelResponse {\n");
    
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    departureTime: ").append(toIndentedString(departureTime)).append("\n");
    sb.append("    arrivalTime: ").append(toIndentedString(arrivalTime)).append("\n");
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

