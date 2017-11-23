package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AccessibilityBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class AccessibilityBase   {
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
    
    WALKINGSTICK("WALKINGSTICK"),
    
    AUDIO_NAVIGATOR("AUDIO_NAVIGATOR"),
    
    VISUAL_NAVIGATOR("VISUAL_NAVIGATOR"),
    
    UMBRELLA("UMBRELLA"),
    
    BUGGY("BUGGY"),
    
    SUITABLE_FOR_WHEELCHAIRS("SUITABLE_FOR_WHEELCHAIRS"),
    
    SUITABLE_FOR_HEAVILY_DISABLED("SUITABLE_FOR_HEAVILY_DISABLED"),
    
    TACTILE_PLATFORM_EDGES("TACTILE_PLATFORM_EDGES"),
    
    TACTILE_GUIDING_STRIPS("TACTILE_GUIDING_STRIPS"),
    
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
  protected TitleEnum title = null;

  public AccessibilityBase title(TitleEnum title) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessibilityBase accessibilityBase = (AccessibilityBase) o;
    return Objects.equals(this.title, accessibilityBase.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessibilityBase {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

