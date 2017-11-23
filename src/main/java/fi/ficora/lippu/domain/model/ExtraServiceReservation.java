package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.ExtraService;
import fi.ficora.lippu.domain.model.ProductFare;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Reservation data for the reserved extra service.
 */
@ApiModel(description = "Reservation data for the reserved extra service.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ExtraServiceReservation   {
  @JsonProperty("title")
  private String title = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("fare")
  private ProductFare fare = null;

  @JsonProperty("extraServiceReservationId")
  private String extraServiceReservationId = null;

  public ExtraServiceReservation title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ExtraServiceReservation description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ExtraServiceReservation fare(ProductFare fare) {
    this.fare = fare;
    return this;
  }

   /**
   * Get fare
   * @return fare
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ProductFare getFare() {
    return fare;
  }

  public void setFare(ProductFare fare) {
    this.fare = fare;
  }

  public ExtraServiceReservation extraServiceReservationId(String extraServiceReservationId) {
    this.extraServiceReservationId = extraServiceReservationId;
    return this;
  }

   /**
   * Get extraServiceReservationId
   * @return extraServiceReservationId
  **/
  @ApiModelProperty(value = "")


  public String getExtraServiceReservationId() {
    return extraServiceReservationId;
  }

  public void setExtraServiceReservationId(String extraServiceReservationId) {
    this.extraServiceReservationId = extraServiceReservationId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtraServiceReservation extraServiceReservation = (ExtraServiceReservation) o;
    return Objects.equals(this.title, extraServiceReservation.title) &&
        Objects.equals(this.description, extraServiceReservation.description) &&
        Objects.equals(this.fare, extraServiceReservation.fare) &&
        Objects.equals(this.extraServiceReservationId, extraServiceReservation.extraServiceReservationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, fare, extraServiceReservationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtraServiceReservation {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    fare: ").append(toIndentedString(fare)).append("\n");
    sb.append("    extraServiceReservationId: ").append(toIndentedString(extraServiceReservationId)).append("\n");
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

