package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.Accessibility;
import fi.ficora.lippu.domain.model.CoordinateLocation;
import fi.ficora.lippu.domain.model.ExtraService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Product
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

public class Product   {
  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("contract")
  private String contract = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("validFrom")
  private OffsetDateTime validFrom = null;

  @JsonProperty("validTo")
  private OffsetDateTime validTo = null;

  @JsonProperty("extraServices")
  @Valid
  private List<ExtraService> extraServices = null;

  @JsonProperty("accessibility")
  @Valid
  private List<Accessibility> accessibility = null;

  @JsonProperty("from")
  private CoordinateLocation from = null;

  @JsonProperty("to")
  private CoordinateLocation to = null;

  public Product productType(String productType) {
    this.productType = productType;
    return this;
  }

   /**
   * Unique product identification.
   * @return productType
  **/
  @ApiModelProperty(required = true, value = "Unique product identification.")
  @NotNull


  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Product contract(String contract) {
    this.contract = contract;
    return this;
  }

   /**
   * Contract identifier
   * @return contract
  **/
  @ApiModelProperty(required = true, value = "Contract identifier")
  @NotNull


  public String getContract() {
    return contract;
  }

  public void setContract(String contract) {
    this.contract = contract;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Product name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "Product name")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Product desdription
   * @return description
  **/
  @ApiModelProperty(value = "Product desdription")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product validFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
    return this;
  }

   /**
   * Starting datetime of product validity. If value is not given, current datetime is used.
   * @return validFrom
  **/
  @ApiModelProperty(value = "Starting datetime of product validity. If value is not given, current datetime is used.")

  @Valid

  public OffsetDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public Product validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * End datetime of product validity. If value is not given, product is valid indefinitely.
   * @return validTo
  **/
  @ApiModelProperty(value = "End datetime of product validity. If value is not given, product is valid indefinitely.")

  @Valid

  public OffsetDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }

  public Product extraServices(List<ExtraService> extraServices) {
    this.extraServices = extraServices;
    return this;
  }

  public Product addExtraServicesItem(ExtraService extraServicesItem) {
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

  public Product accessibility(List<Accessibility> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public Product addAccessibilityItem(Accessibility accessibilityItem) {
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

  public Product from(CoordinateLocation from) {
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CoordinateLocation getFrom() {
    return from;
  }

  public void setFrom(CoordinateLocation from) {
    this.from = from;
  }

  public Product to(CoordinateLocation to) {
    this.to = to;
    return this;
  }

   /**
   * Get to
   * @return to
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CoordinateLocation getTo() {
    return to;
  }

  public void setTo(CoordinateLocation to) {
    this.to = to;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.productType, product.productType) &&
        Objects.equals(this.contract, product.contract) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.description, product.description) &&
        Objects.equals(this.validFrom, product.validFrom) &&
        Objects.equals(this.validTo, product.validTo) &&
        Objects.equals(this.extraServices, product.extraServices) &&
        Objects.equals(this.accessibility, product.accessibility) &&
        Objects.equals(this.from, product.from) &&
        Objects.equals(this.to, product.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, contract, name, description, validFrom, validTo, extraServices, accessibility, from, to);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    contract: ").append(toIndentedString(contract)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    extraServices: ").append(toIndentedString(extraServices)).append("\n");
    sb.append("    accessibility: ").append(toIndentedString(accessibility)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
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

