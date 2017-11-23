package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.domain.model.Accessibility;
import fi.ficora.lippu.domain.model.CoordinateLocation;
import fi.ficora.lippu.domain.model.ExtraService;
import fi.ficora.lippu.domain.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProductDescription
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ProductDescription   {
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

  @JsonProperty("suitablePassengerCategories")
  @Valid
  private List<String> suitablePassengerCategories = null;

  public ProductDescription productType(String productType) {
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

  public ProductDescription contract(String contract) {
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

  public ProductDescription name(String name) {
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

  public ProductDescription description(String description) {
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

  public ProductDescription validFrom(OffsetDateTime validFrom) {
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

  public ProductDescription validTo(OffsetDateTime validTo) {
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

  public ProductDescription extraServices(List<ExtraService> extraServices) {
    this.extraServices = extraServices;
    return this;
  }

  public ProductDescription addExtraServicesItem(ExtraService extraServicesItem) {
    if (this.extraServices == null) {
      this.extraServices = new ArrayList<>();
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

  public ProductDescription accessibility(List<Accessibility> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public ProductDescription addAccessibilityItem(Accessibility accessibilityItem) {
    if (this.accessibility == null) {
      this.accessibility = new ArrayList<>();
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

  public ProductDescription from(CoordinateLocation from) {
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

  public ProductDescription to(CoordinateLocation to) {
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

  public ProductDescription suitablePassengerCategories(List<String> suitablePassengerCategories) {
    this.suitablePassengerCategories = suitablePassengerCategories;
    return this;
  }

  public ProductDescription addSuitablePassengerCategoriesItem(String suitablePassengerCategoriesItem) {
    if (this.suitablePassengerCategories == null) {
      this.suitablePassengerCategories = new ArrayList<>();
    }
    this.suitablePassengerCategories.add(suitablePassengerCategoriesItem);
    return this;
  }

   /**
   * Passenger types that are able to use related product. Values must match returned values of products response.
   * @return suitablePassengerCategories
  **/
  @ApiModelProperty(value = "Passenger types that are able to use related product. Values must match returned values of products response.")


  public List<String> getSuitablePassengerCategories() {
    return suitablePassengerCategories;
  }

  public void setSuitablePassengerCategories(List<String> suitablePassengerCategories) {
    this.suitablePassengerCategories = suitablePassengerCategories;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductDescription productDescription = (ProductDescription) o;
    return Objects.equals(this.productType, productDescription.productType) &&
        Objects.equals(this.contract, productDescription.contract) &&
        Objects.equals(this.name, productDescription.name) &&
        Objects.equals(this.description, productDescription.description) &&
        Objects.equals(this.validFrom, productDescription.validFrom) &&
        Objects.equals(this.validTo, productDescription.validTo) &&
        Objects.equals(this.extraServices, productDescription.extraServices) &&
        Objects.equals(this.accessibility, productDescription.accessibility) &&
        Objects.equals(this.from, productDescription.from) &&
        Objects.equals(this.to, productDescription.to) &&
        Objects.equals(this.suitablePassengerCategories, productDescription.suitablePassengerCategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productType, contract, name, description, validFrom, validTo, extraServices, accessibility, from, to, suitablePassengerCategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductDescription {\n");
    
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
    sb.append("    suitablePassengerCategories: ").append(toIndentedString(suitablePassengerCategories)).append("\n");
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

