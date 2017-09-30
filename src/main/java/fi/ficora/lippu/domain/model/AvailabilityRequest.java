package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.domain.model.TravelPassenger;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AvailabilityRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class AvailabilityRequest   {
  @JsonProperty("contract")
  private String contract = null;

  @JsonProperty("travel")
  private Travel travel = null;

  @JsonProperty("passengers")
  @Valid
  private List<TravelPassenger> passengers = null;

  public AvailabilityRequest contract(String contract) {
    this.contract = contract;
    return this;
  }

   /**
   * Contract identifier
   * @return contract
  **/
  @ApiModelProperty(value = "Contract identifier")


  public String getContract() {
    return contract;
  }

  public void setContract(String contract) {
    this.contract = contract;
  }

  public AvailabilityRequest travel(Travel travel) {
    this.travel = travel;
    return this;
  }

   /**
   * Get travel
   * @return travel
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Travel getTravel() {
    return travel;
  }

  public void setTravel(Travel travel) {
    this.travel = travel;
  }

  public AvailabilityRequest passengers(List<TravelPassenger> passengers) {
    this.passengers = passengers;
    return this;
  }

  public AvailabilityRequest addPassengersItem(TravelPassenger passengersItem) {
    if (this.passengers == null) {
      this.passengers = new ArrayList<TravelPassenger>();
    }
    this.passengers.add(passengersItem);
    return this;
  }

   /**
   * Get passengers
   * @return passengers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TravelPassenger> getPassengers() {
    return passengers;
  }

  public void setPassengers(List<TravelPassenger> passengers) {
    this.passengers = passengers;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvailabilityRequest availabilityRequest = (AvailabilityRequest) o;
    return Objects.equals(this.contract, availabilityRequest.contract) &&
        Objects.equals(this.travel, availabilityRequest.travel) &&
        Objects.equals(this.passengers, availabilityRequest.passengers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contract, travel, passengers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailabilityRequest {\n");
    
    sb.append("    contract: ").append(toIndentedString(contract)).append("\n");
    sb.append("    travel: ").append(toIndentedString(travel)).append("\n");
    sb.append("    passengers: ").append(toIndentedString(passengers)).append("\n");
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

