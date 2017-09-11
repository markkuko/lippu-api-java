package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * CoordinateLocation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

public class CoordinateLocation   {
  @JsonProperty("lat")
  private Double lat = null;

  @JsonProperty("lon")
  private Double lon = null;

  @JsonProperty("description")
  private String description = null;

  public CoordinateLocation lat(Double lat) {
    this.lat = lat;
    return this;
  }

   /**
   * Location coordinate latitude value in WGS-84 coordinatesystem.
   * @return lat
  **/
  @ApiModelProperty(required = true, value = "Location coordinate latitude value in WGS-84 coordinatesystem.")
  @NotNull


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
  @ApiModelProperty(required = true, value = "Location coordinate longitude value in WGS-84 coordinatesystem.")
  @NotNull


  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public CoordinateLocation description(String description) {
    this.description = description;
    return this;
  }

   /**
   * 
   * @return description
  **/
  @ApiModelProperty(value = "")


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
    CoordinateLocation coordinateLocation = (CoordinateLocation) o;
    return Objects.equals(this.lat, coordinateLocation.lat) &&
        Objects.equals(this.lon, coordinateLocation.lon) &&
        Objects.equals(this.description, coordinateLocation.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lon, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoordinateLocation {\n");
    
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
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

