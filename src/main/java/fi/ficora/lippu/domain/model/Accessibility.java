package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fi.ficora.lippu.domain.model.ProductFare;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Accessibility
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class Accessibility   {
  /**
   * Gets or Sets title
   */
  public enum TitleEnum {
    WHEELCHAIR("WHEELCHAIR"),
    
    PUSHCHAIR("PUSHCHAIR"),
    
    PASSENGER_CART("PASSENGER_CART"),
    
    LOW_FLOOR("LOW-FLOOR"),
    
    GUIDE_DOG("GUIDE_DOG"),
    
    ONBOARD_ASSISTANCE("ONBOARD_ASSISTANCE"),
    
    BOARDING_ASSISTANCE("BOARDING_ASSISTANCE"),
    
    UNACCOMPANIED_MINOR_ASSISTANCE("UNACCOMPANIED_MINOR_ASSISTANCE"),
    
    OTHER("OTHER"),
    
    STEP_FREE_ACCESS("STEP_FREE_ACCESS"),
    
    UNKNOWN("UNKNOWN");

    private String value;

    TitleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TitleEnum fromValue(String text) {
      for (TitleEnum b : TitleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("title")
  private TitleEnum title = null;

  @JsonProperty("additionalInformation")
  private String additionalInformation = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("fare")
  private ProductFare fare = null;

  public Accessibility title(TitleEnum title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public TitleEnum getTitle() {
    return title;
  }

  public void setTitle(TitleEnum title) {
    this.title = title;
  }

  public Accessibility additionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
    return this;
  }

   /**
   * Get additionalInformation
   * @return additionalInformation
  **/
  @ApiModelProperty(value = "")


  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public Accessibility description(String description) {
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

  public Accessibility fare(ProductFare fare) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Accessibility accessibility = (Accessibility) o;
    return Objects.equals(this.title, accessibility.title) &&
        Objects.equals(this.additionalInformation, accessibility.additionalInformation) &&
        Objects.equals(this.description, accessibility.description) &&
        Objects.equals(this.fare, accessibility.fare);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, additionalInformation, description, fare);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Accessibility {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    additionalInformation: ").append(toIndentedString(additionalInformation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    fare: ").append(toIndentedString(fare)).append("\n");
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

