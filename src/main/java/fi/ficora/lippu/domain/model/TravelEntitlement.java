package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.AccessibilityReservation;
import fi.ficora.lippu.domain.model.ExtraServiceReservation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TravelEntitlement
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class TravelEntitlement   {
  @JsonProperty("travelEntitlementId")
  private String travelEntitlementId = null;

  @JsonProperty("caseId")
  private String caseId = null;

  @JsonProperty("ticketPayload")
  private String ticketPayload = null;

  @JsonProperty("ticketType")
  private String ticketType = null;

  @JsonProperty("validFrom")
  private OffsetDateTime validFrom = null;

  @JsonProperty("validTo")
  private OffsetDateTime validTo = null;

  @JsonProperty("activated")
  private Boolean activated = null;

  @JsonProperty("extraServices")
  @Valid
  private List<ExtraServiceReservation> extraServices = null;

  @JsonProperty("accessibility")
  @Valid
  private List<AccessibilityReservation> accessibility = null;

  public TravelEntitlement travelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
    return this;
  }

   /**
   * The id for the travel entitlement.
   * @return travelEntitlementId
  **/
  @ApiModelProperty(required = true, value = "The id for the travel entitlement.")
  @NotNull


  public String getTravelEntitlementId() {
    return travelEntitlementId;
  }

  public void setTravelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
  }

  public TravelEntitlement caseId(String caseId) {
    this.caseId = caseId;
    return this;
  }

   /**
   * The reservation identifier, caseId.
   * @return caseId
  **/
  @ApiModelProperty(required = true, value = "The reservation identifier, caseId.")
  @NotNull


  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public TravelEntitlement ticketPayload(String ticketPayload) {
    this.ticketPayload = ticketPayload;
    return this;
  }

   /**
   * Ticket payload. Format is agreed by API partners.
   * @return ticketPayload
  **/
  @ApiModelProperty(required = true, value = "Ticket payload. Format is agreed by API partners.")
  @NotNull


  public String getTicketPayload() {
    return ticketPayload;
  }

  public void setTicketPayload(String ticketPayload) {
    this.ticketPayload = ticketPayload;
  }

  public TravelEntitlement ticketType(String ticketType) {
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

  public TravelEntitlement validFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
    return this;
  }

   /**
   * Ticket payload valid from datetime. Ticket payload cannot be used outside of validity period.
   * @return validFrom
  **/
  @ApiModelProperty(required = true, value = "Ticket payload valid from datetime. Ticket payload cannot be used outside of validity period.")
  @NotNull

  @Valid

  public OffsetDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public TravelEntitlement validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * Ticket payload valid to datetime. Ticket payload cannot be used outside of validity period.
   * @return validTo
  **/
  @ApiModelProperty(required = true, value = "Ticket payload valid to datetime. Ticket payload cannot be used outside of validity period.")
  @NotNull

  @Valid

  public OffsetDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }

  public TravelEntitlement activated(Boolean activated) {
    this.activated = activated;
    return this;
  }

   /**
   * Boolean value is the travel entitlement activated.
   * @return activated
  **/
  @ApiModelProperty(required = true, value = "Boolean value is the travel entitlement activated.")
  @NotNull


  public Boolean isActivated() {
    return activated;
  }

  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  public TravelEntitlement extraServices(List<ExtraServiceReservation> extraServices) {
    this.extraServices = extraServices;
    return this;
  }

  public TravelEntitlement addExtraServicesItem(ExtraServiceReservation extraServicesItem) {
    if (this.extraServices == null) {
      this.extraServices = new ArrayList<>();
    }
    this.extraServices.add(extraServicesItem);
    return this;
  }

   /**
   * Get extraServices
   * @return extraServices
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ExtraServiceReservation> getExtraServices() {
    return extraServices;
  }

  public void setExtraServices(List<ExtraServiceReservation> extraServices) {
    this.extraServices = extraServices;
  }

  public TravelEntitlement accessibility(List<AccessibilityReservation> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public TravelEntitlement addAccessibilityItem(AccessibilityReservation accessibilityItem) {
    if (this.accessibility == null) {
      this.accessibility = new ArrayList<>();
    }
    this.accessibility.add(accessibilityItem);
    return this;
  }

   /**
   * Get accessibility
   * @return accessibility
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<AccessibilityReservation> getAccessibility() {
    return accessibility;
  }

  public void setAccessibility(List<AccessibilityReservation> accessibility) {
    this.accessibility = accessibility;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TravelEntitlement travelEntitlement = (TravelEntitlement) o;
    return Objects.equals(this.travelEntitlementId, travelEntitlement.travelEntitlementId) &&
        Objects.equals(this.caseId, travelEntitlement.caseId) &&
        Objects.equals(this.ticketPayload, travelEntitlement.ticketPayload) &&
        Objects.equals(this.ticketType, travelEntitlement.ticketType) &&
        Objects.equals(this.validFrom, travelEntitlement.validFrom) &&
        Objects.equals(this.validTo, travelEntitlement.validTo) &&
        Objects.equals(this.activated, travelEntitlement.activated) &&
        Objects.equals(this.extraServices, travelEntitlement.extraServices) &&
        Objects.equals(this.accessibility, travelEntitlement.accessibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(travelEntitlementId, caseId, ticketPayload, ticketType, validFrom, validTo, activated, extraServices, accessibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelEntitlement {\n");
    
    sb.append("    travelEntitlementId: ").append(toIndentedString(travelEntitlementId)).append("\n");
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    ticketPayload: ").append(toIndentedString(ticketPayload)).append("\n");
    sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    extraServices: ").append(toIndentedString(extraServices)).append("\n");
    sb.append("    accessibility: ").append(toIndentedString(accessibility)).append("\n");
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

