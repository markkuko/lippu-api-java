package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transport
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class Transport   {
  @JsonProperty("operator")
  private String operator = null;

  @JsonProperty("vehicleInfo")
  private String vehicleInfo = null;

  @JsonProperty("seatInfo")
  private String seatInfo = null;

  public Transport operator(String operator) {
    this.operator = operator;
    return this;
  }

   /**
   * Name of the transport operator.
   * @return operator
  **/
  @ApiModelProperty(required = true, value = "Name of the transport operator.")
  @NotNull


  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Transport vehicleInfo(String vehicleInfo) {
    this.vehicleInfo = vehicleInfo;
    return this;
  }

   /**
   * Description of transport operator.
   * @return vehicleInfo
  **/
  @ApiModelProperty(required = true, value = "Description of transport operator.")
  @NotNull


  public String getVehicleInfo() {
    return vehicleInfo;
  }

  public void setVehicleInfo(String vehicleInfo) {
    this.vehicleInfo = vehicleInfo;
  }

  public Transport seatInfo(String seatInfo) {
    this.seatInfo = seatInfo;
    return this;
  }

   /**
   * Seat information details of transport vehicle.
   * @return seatInfo
  **/
  @ApiModelProperty(value = "Seat information details of transport vehicle.")


  public String getSeatInfo() {
    return seatInfo;
  }

  public void setSeatInfo(String seatInfo) {
    this.seatInfo = seatInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transport transport = (Transport) o;
    return Objects.equals(this.operator, transport.operator) &&
        Objects.equals(this.vehicleInfo, transport.vehicleInfo) &&
        Objects.equals(this.seatInfo, transport.seatInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operator, vehicleInfo, seatInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transport {\n");
    
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    vehicleInfo: ").append(toIndentedString(vehicleInfo)).append("\n");
    sb.append("    seatInfo: ").append(toIndentedString(seatInfo)).append("\n");
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

