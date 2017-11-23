package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.ReservationRequestReservations;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReservationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ReservationRequest   {
  @JsonProperty("reservations")
  @Valid
  private List<ReservationRequestReservations> reservations = null;

  public ReservationRequest reservations(List<ReservationRequestReservations> reservations) {
    this.reservations = reservations;
    return this;
  }

  public ReservationRequest addReservationsItem(ReservationRequestReservations reservationsItem) {
    if (this.reservations == null) {
      this.reservations = new ArrayList<>();
    }
    this.reservations.add(reservationsItem);
    return this;
  }

   /**
   * Get reservations
   * @return reservations
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ReservationRequestReservations> getReservations() {
    return reservations;
  }

  public void setReservations(List<ReservationRequestReservations> reservations) {
    this.reservations = reservations;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationRequest reservationRequest = (ReservationRequest) o;
    return Objects.equals(this.reservations, reservationRequest.reservations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationRequest {\n");
    
    sb.append("    reservations: ").append(toIndentedString(reservations)).append("\n");
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

