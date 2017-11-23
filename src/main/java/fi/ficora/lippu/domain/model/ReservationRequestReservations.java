package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.CustomerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReservationRequestReservations
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ReservationRequestReservations   {
  @JsonProperty("travelEntitlementId")
  private String travelEntitlementId = null;

  @JsonProperty("ticketType")
  private String ticketType = null;

  @JsonProperty("customerInfo")
  @Valid
  private List<CustomerInfo> customerInfo = null;

  @JsonProperty("chosenExtraReservationIds")
  @Valid
  private List<String> chosenExtraReservationIds = null;

  @JsonProperty("chosenAccessibilityReservationIds")
  @Valid
  private List<String> chosenAccessibilityReservationIds = null;

  public ReservationRequestReservations travelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
    return this;
  }

   /**
   * The id for a travel entitlement from availability response.
   * @return travelEntitlementId
  **/
  @ApiModelProperty(value = "The id for a travel entitlement from availability response.")


  public String getTravelEntitlementId() {
    return travelEntitlementId;
  }

  public void setTravelEntitlementId(String travelEntitlementId) {
    this.travelEntitlementId = travelEntitlementId;
  }

  public ReservationRequestReservations ticketType(String ticketType) {
    this.ticketType = ticketType;
    return this;
  }

   /**
   * Requested type of the ticket. Must be one of the types the transport service supports.
   * @return ticketType
  **/
  @ApiModelProperty(value = "Requested type of the ticket. Must be one of the types the transport service supports.")


  public String getTicketType() {
    return ticketType;
  }

  public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
  }

  public ReservationRequestReservations customerInfo(List<CustomerInfo> customerInfo) {
    this.customerInfo = customerInfo;
    return this;
  }

  public ReservationRequestReservations addCustomerInfoItem(CustomerInfo customerInfoItem) {
    if (this.customerInfo == null) {
      this.customerInfo = new ArrayList<>();
    }
    this.customerInfo.add(customerInfoItem);
    return this;
  }

   /**
   * Get customerInfo
   * @return customerInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CustomerInfo> getCustomerInfo() {
    return customerInfo;
  }

  public void setCustomerInfo(List<CustomerInfo> customerInfo) {
    this.customerInfo = customerInfo;
  }

  public ReservationRequestReservations chosenExtraReservationIds(List<String> chosenExtraReservationIds) {
    this.chosenExtraReservationIds = chosenExtraReservationIds;
    return this;
  }

  public ReservationRequestReservations addChosenExtraReservationIdsItem(String chosenExtraReservationIdsItem) {
    if (this.chosenExtraReservationIds == null) {
      this.chosenExtraReservationIds = new ArrayList<>();
    }
    this.chosenExtraReservationIds.add(chosenExtraReservationIdsItem);
    return this;
  }

   /**
   * Reservation ids of chosen extra services.
   * @return chosenExtraReservationIds
  **/
  @ApiModelProperty(value = "Reservation ids of chosen extra services.")


  public List<String> getChosenExtraReservationIds() {
    return chosenExtraReservationIds;
  }

  public void setChosenExtraReservationIds(List<String> chosenExtraReservationIds) {
    this.chosenExtraReservationIds = chosenExtraReservationIds;
  }

  public ReservationRequestReservations chosenAccessibilityReservationIds(List<String> chosenAccessibilityReservationIds) {
    this.chosenAccessibilityReservationIds = chosenAccessibilityReservationIds;
    return this;
  }

  public ReservationRequestReservations addChosenAccessibilityReservationIdsItem(String chosenAccessibilityReservationIdsItem) {
    if (this.chosenAccessibilityReservationIds == null) {
      this.chosenAccessibilityReservationIds = new ArrayList<>();
    }
    this.chosenAccessibilityReservationIds.add(chosenAccessibilityReservationIdsItem);
    return this;
  }

   /**
   * Reservation ids of chosen accessibility features.
   * @return chosenAccessibilityReservationIds
  **/
  @ApiModelProperty(value = "Reservation ids of chosen accessibility features.")


  public List<String> getChosenAccessibilityReservationIds() {
    return chosenAccessibilityReservationIds;
  }

  public void setChosenAccessibilityReservationIds(List<String> chosenAccessibilityReservationIds) {
    this.chosenAccessibilityReservationIds = chosenAccessibilityReservationIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationRequestReservations reservationRequestReservations = (ReservationRequestReservations) o;
    return Objects.equals(this.travelEntitlementId, reservationRequestReservations.travelEntitlementId) &&
        Objects.equals(this.ticketType, reservationRequestReservations.ticketType) &&
        Objects.equals(this.customerInfo, reservationRequestReservations.customerInfo) &&
        Objects.equals(this.chosenExtraReservationIds, reservationRequestReservations.chosenExtraReservationIds) &&
        Objects.equals(this.chosenAccessibilityReservationIds, reservationRequestReservations.chosenAccessibilityReservationIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(travelEntitlementId, ticketType, customerInfo, chosenExtraReservationIds, chosenAccessibilityReservationIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationRequestReservations {\n");
    
    sb.append("    travelEntitlementId: ").append(toIndentedString(travelEntitlementId)).append("\n");
    sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");
    sb.append("    customerInfo: ").append(toIndentedString(customerInfo)).append("\n");
    sb.append("    chosenExtraReservationIds: ").append(toIndentedString(chosenExtraReservationIds)).append("\n");
    sb.append("    chosenAccessibilityReservationIds: ").append(toIndentedString(chosenAccessibilityReservationIds)).append("\n");
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

