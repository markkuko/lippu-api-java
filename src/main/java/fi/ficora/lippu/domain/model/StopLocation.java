package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * StopLocation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

public class StopLocation   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("platform")
  private String platform = null;

  @JsonProperty("description")
  private String description = null;

  public StopLocation id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Stop location identifier from stop registry both participants are using. Eg. Digiroad2
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Stop location identifier from stop registry both participants are using. Eg. Digiroad2")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public StopLocation platform(String platform) {
    this.platform = platform;
    return this;
  }

   /**
   * Platform information from where service will depart. Meaningfull information in large station areas where transport service can depart from multiple different platforms.
   * @return platform
  **/
  @ApiModelProperty(value = "Platform information from where service will depart. Meaningfull information in large station areas where transport service can depart from multiple different platforms.")


  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public StopLocation description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Stop location description text.
   * @return description
  **/
  @ApiModelProperty(value = "Stop location description text.")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StopLocation stopLocation = (StopLocation) o;
    return Objects.equals(this.id, stopLocation.id) &&
        Objects.equals(this.platform, stopLocation.platform) &&
        Objects.equals(this.description, stopLocation.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, platform, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StopLocation {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

