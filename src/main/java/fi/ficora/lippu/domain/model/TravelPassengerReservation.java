package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.AccessibilityReservation;
import fi.ficora.lippu.domain.model.ExtraServiceReservation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TravelPassengerReservation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class TravelPassengerReservation   {
  @JsonProperty("category")
  private String category = null;

  @JsonProperty("extraServices")
  @Valid
  private List<ExtraServiceReservation> extraServices = null;

  @JsonProperty("accessibility")
  @Valid
  private List<AccessibilityReservation> accessibility = null;

  public TravelPassengerReservation category(String category) {
    this.category = category;
    return this;
  }

   /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public TravelPassengerReservation extraServices(List<ExtraServiceReservation> extraServices) {
    this.extraServices = extraServices;
    return this;
  }

  public TravelPassengerReservation addExtraServicesItem(ExtraServiceReservation extraServicesItem) {
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

  public TravelPassengerReservation accessibility(List<AccessibilityReservation> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public TravelPassengerReservation addAccessibilityItem(AccessibilityReservation accessibilityItem) {
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
    TravelPassengerReservation travelPassengerReservation = (TravelPassengerReservation) o;
    return Objects.equals(this.category, travelPassengerReservation.category) &&
        Objects.equals(this.extraServices, travelPassengerReservation.extraServices) &&
        Objects.equals(this.accessibility, travelPassengerReservation.accessibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, extraServices, accessibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelPassengerReservation {\n");
    
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

