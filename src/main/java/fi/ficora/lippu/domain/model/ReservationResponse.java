package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import fi.ficora.lippu.domain.model.ReservationResponseConfirmedReservations;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * ReservationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class ReservationResponse   {
  @JsonProperty("caseId")
  private String caseId = null;

  @JsonProperty("confirmedReservations")
  @Valid
  private List<ReservationResponseConfirmedReservations> confirmedReservations = null;

  public ReservationResponse caseId(String caseId) {
    this.caseId = caseId;
    return this;
  }

   /**
   * Transaction case id. Eg. can be used to map payment clearing to ticket transactions.
   * @return caseId
  **/
  @ApiModelProperty(value = "Transaction case id. Eg. can be used to map payment clearing to ticket transactions.")


  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public ReservationResponse confirmedReservations(List<ReservationResponseConfirmedReservations> confirmedReservations) {
    this.confirmedReservations = confirmedReservations;
    return this;
  }

  public ReservationResponse addConfirmedReservationsItem(ReservationResponseConfirmedReservations confirmedReservationsItem) {
    if (this.confirmedReservations == null) {
      this.confirmedReservations = new ArrayList<ReservationResponseConfirmedReservations>();
    }
    this.confirmedReservations.add(confirmedReservationsItem);
    return this;
  }

   /**
   * Get confirmedReservations
   * @return confirmedReservations
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ReservationResponseConfirmedReservations> getConfirmedReservations() {
    return confirmedReservations;
  }

  public void setConfirmedReservations(List<ReservationResponseConfirmedReservations> confirmedReservations) {
    this.confirmedReservations = confirmedReservations;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationResponse reservationResponse = (ReservationResponse) o;
    return Objects.equals(this.caseId, reservationResponse.caseId) &&
        Objects.equals(this.confirmedReservations, reservationResponse.confirmedReservations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(caseId, confirmedReservations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationResponse {\n");
    
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    confirmedReservations: ").append(toIndentedString(confirmedReservations)).append("\n");
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

