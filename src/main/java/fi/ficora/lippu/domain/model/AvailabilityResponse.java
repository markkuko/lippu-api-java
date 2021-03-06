package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.TravelAvailability;
import fi.ficora.lippu.domain.model.TravelResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AvailabilityResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-24T14:36:56.606+02:00")

public class AvailabilityResponse   {
  @JsonProperty("contract")
  private String contract = null;

  @JsonProperty("travel")
  private TravelResponse travel = null;

  @JsonProperty("supportedTicketTypes")
  @Valid
  private List<String> supportedTicketTypes = null;

  @JsonProperty("availability")
  @Valid
  private List<TravelAvailability> availability = new ArrayList<>();

  public AvailabilityResponse contract(String contract) {
    this.contract = contract;
    return this;
  }

   /**
   * Contract identifier
   * @return contract
  **/
  @ApiModelProperty(required = true, value = "Contract identifier")
  @NotNull


  public String getContract() {
    return contract;
  }

  public void setContract(String contract) {
    this.contract = contract;
  }

  public AvailabilityResponse travel(TravelResponse travel) {
    this.travel = travel;
    return this;
  }

   /**
   * Get travel
   * @return travel
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TravelResponse getTravel() {
    return travel;
  }

  public void setTravel(TravelResponse travel) {
    this.travel = travel;
  }

  public AvailabilityResponse supportedTicketTypes(List<String> supportedTicketTypes) {
    this.supportedTicketTypes = supportedTicketTypes;
    return this;
  }

  public AvailabilityResponse addSupportedTicketTypesItem(String supportedTicketTypesItem) {
    if (this.supportedTicketTypes == null) {
      this.supportedTicketTypes = new ArrayList<>();
    }
    this.supportedTicketTypes.add(supportedTicketTypesItem);
    return this;
  }

   /**
   * List of ticket types the trasport service supports. For example QR code, barcode, NFC.
   * @return supportedTicketTypes
  **/
  @ApiModelProperty(value = "List of ticket types the trasport service supports. For example QR code, barcode, NFC.")


  public List<String> getSupportedTicketTypes() {
    return supportedTicketTypes;
  }

  public void setSupportedTicketTypes(List<String> supportedTicketTypes) {
    this.supportedTicketTypes = supportedTicketTypes;
  }

  public AvailabilityResponse availability(List<TravelAvailability> availability) {
    this.availability = availability;
    return this;
  }

  public AvailabilityResponse addAvailabilityItem(TravelAvailability availabilityItem) {
    this.availability.add(availabilityItem);
    return this;
  }

   /**
   * Get availability
   * @return availability
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<TravelAvailability> getAvailability() {
    return availability;
  }

  public void setAvailability(List<TravelAvailability> availability) {
    this.availability = availability;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvailabilityResponse availabilityResponse = (AvailabilityResponse) o;
    return Objects.equals(this.contract, availabilityResponse.contract) &&
        Objects.equals(this.travel, availabilityResponse.travel) &&
        Objects.equals(this.supportedTicketTypes, availabilityResponse.supportedTicketTypes) &&
        Objects.equals(this.availability, availabilityResponse.availability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contract, travel, supportedTicketTypes, availability);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailabilityResponse {\n");
    
    sb.append("    contract: ").append(toIndentedString(contract)).append("\n");
    sb.append("    travel: ").append(toIndentedString(travel)).append("\n");
    sb.append("    supportedTicketTypes: ").append(toIndentedString(supportedTicketTypes)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
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

