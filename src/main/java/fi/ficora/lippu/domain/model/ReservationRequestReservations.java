package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import fi.ficora.lippu.domain.model.CustomerInfo;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * ReservationRequestReservations
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class ReservationRequestReservations   {
  @JsonProperty("reservationData")
  private String reservationData = null;

  @JsonProperty("customerInfo")
  @Valid
  private List<CustomerInfo> customerInfo = null;

  @JsonProperty("chosenExtraReservationDatas")
  @Valid
  private List<String> chosenExtraReservationDatas = null;

  public ReservationRequestReservations reservationData(String reservationData) {
    this.reservationData = reservationData;
    return this;
  }

   /**
   * Reservation data from availability response
   * @return reservationData
  **/
  @ApiModelProperty(value = "Reservation data from availability response")


  public String getReservationData() {
    return reservationData;
  }

  public void setReservationData(String reservationData) {
    this.reservationData = reservationData;
  }

  public ReservationRequestReservations customerInfo(List<CustomerInfo> customerInfo) {
    this.customerInfo = customerInfo;
    return this;
  }

  public ReservationRequestReservations addCustomerInfoItem(CustomerInfo customerInfoItem) {
    if (this.customerInfo == null) {
      this.customerInfo = new ArrayList<CustomerInfo>();
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

  public ReservationRequestReservations chosenExtraReservationDatas(List<String> chosenExtraReservationDatas) {
    this.chosenExtraReservationDatas = chosenExtraReservationDatas;
    return this;
  }

  public ReservationRequestReservations addChosenExtraReservationDatasItem(String chosenExtraReservationDatasItem) {
    if (this.chosenExtraReservationDatas == null) {
      this.chosenExtraReservationDatas = new ArrayList<String>();
    }
    this.chosenExtraReservationDatas.add(chosenExtraReservationDatasItem);
    return this;
  }

   /**
   * Reservation data of chosen extra services.
   * @return chosenExtraReservationDatas
  **/
  @ApiModelProperty(value = "Reservation data of chosen extra services.")


  public List<String> getChosenExtraReservationDatas() {
    return chosenExtraReservationDatas;
  }

  public void setChosenExtraReservationDatas(List<String> chosenExtraReservationDatas) {
    this.chosenExtraReservationDatas = chosenExtraReservationDatas;
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
    return Objects.equals(this.reservationData, reservationRequestReservations.reservationData) &&
        Objects.equals(this.customerInfo, reservationRequestReservations.customerInfo) &&
        Objects.equals(this.chosenExtraReservationDatas, reservationRequestReservations.chosenExtraReservationDatas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationData, customerInfo, chosenExtraReservationDatas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationRequestReservations {\n");
    
    sb.append("    reservationData: ").append(toIndentedString(reservationData)).append("\n");
    sb.append("    customerInfo: ").append(toIndentedString(customerInfo)).append("\n");
    sb.append("    chosenExtraReservationDatas: ").append(toIndentedString(chosenExtraReservationDatas)).append("\n");
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

