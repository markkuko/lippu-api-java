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
 * ReservationDeleteResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class ReservationDeleteResponse   {
  @JsonProperty("cancellation")
  private Boolean cancellation = null;

  public ReservationDeleteResponse cancellation(Boolean cancellation) {
    this.cancellation = cancellation;
    return this;
  }

   /**
   * Result of the cancellation operation
   * @return cancellation
  **/
  @ApiModelProperty(value = "Result of the cancellation operation")


  public Boolean isCancellation() {
    return cancellation;
  }

  public void setCancellation(Boolean cancellation) {
    this.cancellation = cancellation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationDeleteResponse reservationDeleteResponse = (ReservationDeleteResponse) o;
    return Objects.equals(this.cancellation, reservationDeleteResponse.cancellation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cancellation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationDeleteResponse {\n");
    
    sb.append("    cancellation: ").append(toIndentedString(cancellation)).append("\n");
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

