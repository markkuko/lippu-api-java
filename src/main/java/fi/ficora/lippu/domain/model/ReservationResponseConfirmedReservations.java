package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReservationResponseConfirmedReservations
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ReservationResponseConfirmedReservations   {
  @JsonProperty("travelEntitlementId")
  private String travelEntitlementId = null;

  @JsonProperty("ticketPayload")
  private String ticketPayload = null;

  @JsonProperty("ticketType")
  private String ticketType = null;

  @JsonProperty("validFrom")
  private OffsetDateTime validFrom = null;

  @JsonProperty("validTo")
  private OffsetDateTime validTo = null;

  public ReservationResponseConfirmedReservations travelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
    return this;
  }

   /**
   * The id for the travel entitlement.
   * @return travelEntitlementId
  **/
  @ApiModelProperty(value = "The id for the travel entitlement.")


  public String getTravelEntitlementId() {
    return travelEntitlementId;
  }

  public void setTravelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
  }

  public ReservationResponseConfirmedReservations ticketPayload(String ticketPayload) {
    this.ticketPayload = ticketPayload;
    return this;
  }

   /**
   * Ticket payload. Format is agreed by API partners, ticketType can be used to distinguish different formats.
   * @return ticketPayload
  **/
  @ApiModelProperty(value = "Ticket payload. Format is agreed by API partners, ticketType can be used to distinguish different formats.")


  public String getTicketPayload() {
    return ticketPayload;
  }

  public void setTicketPayload(String ticketPayload) {
    this.ticketPayload = ticketPayload;
  }

  public ReservationResponseConfirmedReservations ticketType(String ticketType) {
    this.ticketType = ticketType;
    return this;
  }

   /**
   * The ticket type for the ticket payload, for example NFC, QR code, Barcode or Bluetooth. Different formats is agreed by API partners.
   * @return ticketType
  **/
  @ApiModelProperty(value = "The ticket type for the ticket payload, for example NFC, QR code, Barcode or Bluetooth. Different formats is agreed by API partners.")


  public String getTicketType() {
    return ticketType;
  }

  public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
  }

  public ReservationResponseConfirmedReservations validFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
    return this;
  }

   /**
   * Ticket payload valid from datetime. Ticket payload cannot be used outside of validity period.
   * @return validFrom
  **/
  @ApiModelProperty(value = "Ticket payload valid from datetime. Ticket payload cannot be used outside of validity period.")

  @Valid

  public OffsetDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public ReservationResponseConfirmedReservations validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * Ticket payload valid to datetime. Ticket payload cannot be used outside of validity period.
   * @return validTo
  **/
  @ApiModelProperty(value = "Ticket payload valid to datetime. Ticket payload cannot be used outside of validity period.")

  @Valid

  public OffsetDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationResponseConfirmedReservations reservationResponseConfirmedReservations = (ReservationResponseConfirmedReservations) o;
    return Objects.equals(this.travelEntitlementId, reservationResponseConfirmedReservations.travelEntitlementId) &&
        Objects.equals(this.ticketPayload, reservationResponseConfirmedReservations.ticketPayload) &&
        Objects.equals(this.ticketType, reservationResponseConfirmedReservations.ticketType) &&
        Objects.equals(this.validFrom, reservationResponseConfirmedReservations.validFrom) &&
        Objects.equals(this.validTo, reservationResponseConfirmedReservations.validTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(travelEntitlementId, ticketPayload, ticketType, validFrom, validTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationResponseConfirmedReservations {\n");
    
    sb.append("    travelEntitlementId: ").append(toIndentedString(travelEntitlementId)).append("\n");
    sb.append("    ticketPayload: ").append(toIndentedString(ticketPayload)).append("\n");
    sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
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

