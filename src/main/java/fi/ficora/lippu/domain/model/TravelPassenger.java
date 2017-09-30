package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.Accessibility;
import fi.ficora.lippu.domain.model.ExtraService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TravelPassenger
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class TravelPassenger   {
  @JsonProperty("category")
  private String category = null;

  @JsonProperty("extraServices")
  @Valid
  private List<ExtraService> extraServices = null;

  @JsonProperty("accessibility")
  @Valid
  private List<Accessibility> accessibility = null;

  public TravelPassenger category(String category) {
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

  public TravelPassenger extraServices(List<ExtraService> extraServices) {
    this.extraServices = extraServices;
    return this;
  }

  public TravelPassenger addExtraServicesItem(ExtraService extraServicesItem) {
    if (this.extraServices == null) {
      this.extraServices = new ArrayList<ExtraService>();
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

  public List<ExtraService> getExtraServices() {
    return extraServices;
  }

  public void setExtraServices(List<ExtraService> extraServices) {
    this.extraServices = extraServices;
  }

  public TravelPassenger accessibility(List<Accessibility> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public TravelPassenger addAccessibilityItem(Accessibility accessibilityItem) {
    if (this.accessibility == null) {
      this.accessibility = new ArrayList<Accessibility>();
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

  public List<Accessibility> getAccessibility() {
    return accessibility;
  }

  public void setAccessibility(List<Accessibility> accessibility) {
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
    TravelPassenger travelPassenger = (TravelPassenger) o;
    return Objects.equals(this.category, travelPassenger.category) &&
        Objects.equals(this.extraServices, travelPassenger.extraServices) &&
        Objects.equals(this.accessibility, travelPassenger.accessibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, extraServices, accessibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TravelPassenger {\n");
    
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

