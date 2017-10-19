package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Error response message in JSON format.
 */
@ApiModel(description = "Error response message in JSON format.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class ApiError   {
  @JsonProperty("statusCode")
  private BigDecimal statusCode = null;

  @JsonProperty("message")
  private String message = null;

  public ApiError statusCode(BigDecimal statusCode) {
    this.statusCode = statusCode;
    return this;
  }

   /**
   * HTTP statuscode of the error message.
   * @return statusCode
  **/
  @ApiModelProperty(required = true, value = "HTTP statuscode of the error message.")
  @NotNull

  @Valid

  public BigDecimal getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(BigDecimal statusCode) {
    this.statusCode = statusCode;
  }

  public ApiError message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Error message description.
   * @return message
  **/
  @ApiModelProperty(required = true, value = "Error message description.")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(this.statusCode, apiError.statusCode) &&
        Objects.equals(this.message, apiError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

