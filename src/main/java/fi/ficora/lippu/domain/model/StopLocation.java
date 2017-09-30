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
 * StopLocation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class StopLocation   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("platform")
  private String platform = null;

  public StopLocation id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Stop location identifier from stop registry both participants are using. Eg. Digiroad2
   * @return id
  **/
  @ApiModelProperty(value = "Stop location identifier from stop registry both participants are using. Eg. Digiroad2")


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
        Objects.equals(this.platform, stopLocation.platform);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, platform);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StopLocation {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
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

