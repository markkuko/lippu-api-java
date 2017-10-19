package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * Location description for the point of departure or point of destination. Can be stop a location with using stopId and stopPlatform (for example a bus stop) or a coordinate locate using lat and lon numbers.
 */
@ApiModel(description = "Location description for the point of departure or point of destination. Can be stop a location with using stopId and stopPlatform (for example a bus stop) or a coordinate locate using lat and lon numbers.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class CombinedLocation   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("platform")
  private String platform = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("lat")
  private Double lat = null;

  @JsonProperty("lon")
  private Double lon = null;

  public CombinedLocation id(String id) {
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

  public CombinedLocation platform(String platform) {
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

  public CombinedLocation description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Location description text.
   * @return description
  **/
  @ApiModelProperty(value = "Location description text.")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CombinedLocation lat(Double lat) {
    this.lat = lat;
    return this;
  }

   /**
   * Location coordinate latitude value in WGS-84 coordinatesystem.
   * @return lat
  **/
  @ApiModelProperty(value = "Location coordinate latitude value in WGS-84 coordinatesystem.")


  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public CombinedLocation lon(Double lon) {
    this.lon = lon;
    return this;
  }

   /**
   * Location coordinate longitude value in WGS-84 coordinatesystem.
   * @return lon
  **/
  @ApiModelProperty(value = "Location coordinate longitude value in WGS-84 coordinatesystem.")


  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CombinedLocation combinedLocation = (CombinedLocation) o;
    return Objects.equals(this.id, combinedLocation.id) &&
        Objects.equals(this.platform, combinedLocation.platform) &&
        Objects.equals(this.description, combinedLocation.description) &&
        Objects.equals(this.lat, combinedLocation.lat) &&
        Objects.equals(this.lon, combinedLocation.lon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, platform, description, lat, lon);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CombinedLocation {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
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

