package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.ProductFare;
import fi.ficora.lippu.domain.model.Transport;
import fi.ficora.lippu.domain.model.TravelPassenger;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TravelAvailability
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class TravelAvailability   {
  @JsonProperty("reservationData")
  private String reservationData = null;

  @JsonProperty("validTo")
  private OffsetDateTime validTo = null;

  @JsonProperty("fare")
  private ProductFare fare = null;

  @JsonProperty("transport")
  private Transport transport = null;

  @JsonProperty("applicableForPassengers")
  @Valid
  private List<TravelPassenger> applicableForPassengers = null;

  public TravelAvailability reservationData(String reservationData) {
    this.reservationData = reservationData;
    return this;
  }

   /**
   * Ticket supply information provided by the transportation service. With this information transport service is able to identify offered service. Content can be database sequence, calculated identifier or crypted capacity data that after decryption describes ordered service to transportation operator. This alone should be enough to identify where and when passenger wants to go, and what kind of product he/she is using.
   * @return reservationData
  **/
  @ApiModelProperty(value = "Ticket supply information provided by the transportation service. With this information transport service is able to identify offered service. Content can be database sequence, calculated identifier or crypted capacity data that after decryption describes ordered service to transportation operator. This alone should be enough to identify where and when passenger wants to go, and what kind of product he/she is using.")


  public String getReservationData() {
    return reservationData;
  }

  public void setReservationData(String reservationData) {
    this.reservationData = reservationData;
  }

  public TravelAvailability validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * End datetime of capacity offer validity. Offered capacity cannot be used outside of validity.
   * @return validTo
  **/
  @ApiModelProperty(value = "End datetime of capacity offer validity. Offered capacity cannot be used outside of validity.")

  @Valid

  public OffsetDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }

  public TravelAvailability fare(ProductFare fare) {
    this.fare = fare;
    return this;
  }

   /**
   * Get fare
   * @return fare
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ProductFare getFare() {
    return fare;
  }

  public void setFare(ProductFare fare) {
    this.fare = fare;
  }

  public TravelAvailability transport(Transport transport) {
    this.transport = transport;
    return this;
  }

   /**
   * Get transport
   * @return transport
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Transport getTransport() {
    return transport;
  }

  public void setTransport(Transport transport) {
    this.transport = transport;
  }

  public TravelAvailability applicableForPassengers(List<TravelPassenger> applicableForPassengers) {
    this.applicableForPassengers = applicableForPassengers;
    return this;
  }

  public TravelAvailability addApplicableForPassengersItem(TravelPassenger applicableForPassengersItem) {
    if (this.applicableForPassengers == null) {
      this.applicableForPassengers = new ArrayList<TravelPassenger>();
    }
    this.applicableForPassengers.add(applicableForPassengersItem);
    return this;
  }

   /**
   * Reference to passenger categories given in availability request.
   * @return applicableForPassengers
  **/
  @ApiModelProperty(value = "Reference to passenger categories given in availability request.")

  @Valid

  public List<TravelPassenger> getApplicableForPassengers() {
    return applicableForPassengers;
  }

  public void setApplicableForPassengers(List<TravelPassenger> applicableForPassengers) {
    this.applicableForPassengers = applicableForPassengers;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TravelAvailability travelAvailability = (TravelAvailability) o;
    return Objects.equals(this.reservationData, travelAvailability.reservationData) &&
        Objects.equals(this.validTo, travelAvailability.validTo) &&
        Objects.equals(this.fare, travelAvailability.fare) &&
        Objects.equals(this.transport, travelAvailability.transport) &&
        Objects.equals(this.applicableForPassengers, travelAvailability.applicableForPassengers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationData, validTo, fare, transport, applicableForPassengers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelAvailability {\n");
    
    sb.append("    reservationData: ").append(toIndentedString(reservationData)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    fare: ").append(toIndentedString(fare)).append("\n");
    sb.append("    transport: ").append(toIndentedString(transport)).append("\n");
    sb.append("    applicableForPassengers: ").append(toIndentedString(applicableForPassengers)).append("\n");
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

