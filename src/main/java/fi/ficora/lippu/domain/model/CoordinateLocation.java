package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * Location description for the point of desparture or point of destination. Can be stop a location with using stopId and stopPlatform (for example a bus stop) or a coordinate locate using lat and lon numbers.
 */
@ApiModel(description = "Location description for the point of desparture or point of destination. Can be stop a location with using stopId and stopPlatform (for example a bus stop) or a coordinate locate using lat and lon numbers.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class CoordinateLocation   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("lat")
  private Double lat = null;

  @JsonProperty("lon")
  private Double lon = null;

  public CoordinateLocation description(String description) {
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

  public CoordinateLocation lat(Double lat) {
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

  public CoordinateLocation lon(Double lon) {
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
    CoordinateLocation coordinateLocation = (CoordinateLocation) o;
    return Objects.equals(this.description, coordinateLocation.description) &&
        Objects.equals(this.lat, coordinateLocation.lat) &&
        Objects.equals(this.lon, coordinateLocation.lon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, lat, lon);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoordinateLocation {\n");
    
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

