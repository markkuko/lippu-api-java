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
 * Voluntarily provided contact information from passenger, eg. in the event of a possible disturbance.
 */
@ApiModel(description = "Voluntarily provided contact information from passenger, eg. in the event of a possible disturbance.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class CustomerInfo   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("phone")
  private String phone = null;

  @JsonProperty("email")
  private String email = null;

  public CustomerInfo name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Passenger name
   * @return name
  **/
  @ApiModelProperty(value = "Passenger name")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomerInfo phone(String phone) {
    this.phone = phone;
    return this;
  }

   /**
   * Passenger phone number
   * @return phone
  **/
  @ApiModelProperty(value = "Passenger phone number")


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public CustomerInfo email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Passenger email address
   * @return email
  **/
  @ApiModelProperty(value = "Passenger email address")


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerInfo customerInfo = (CustomerInfo) o;
    return Objects.equals(this.name, customerInfo.name) &&
        Objects.equals(this.phone, customerInfo.phone) &&
        Objects.equals(this.email, customerInfo.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, phone, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerInfo {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

