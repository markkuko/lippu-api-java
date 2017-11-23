package fi.ficora.lippu.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProductFare
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

public class ProductFare   {
  @JsonProperty("currency")
  private String currency = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("vatPercent")
  private BigDecimal vatPercent = null;

  public ProductFare currency(String currency) {
    this.currency = currency;
    return this;
  }

   /**
   * Currency code in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217) format.
   * @return currency
  **/
  @ApiModelProperty(required = true, value = "Currency code in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217) format.")
  @NotNull


  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public ProductFare amount(Double amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Total price of offered service.
   * @return amount
  **/
  @ApiModelProperty(required = true, value = "Total price of offered service.")
  @NotNull


  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public ProductFare vatPercent(BigDecimal vatPercent) {
    this.vatPercent = vatPercent;
    return this;
  }

   /**
   * VAT percentage of offered service.
   * @return vatPercent
  **/
  @ApiModelProperty(required = true, value = "VAT percentage of offered service.")
  @NotNull

  @Valid

  public BigDecimal getVatPercent() {
    return vatPercent;
  }

  public void setVatPercent(BigDecimal vatPercent) {
    this.vatPercent = vatPercent;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductFare productFare = (ProductFare) o;
    return Objects.equals(this.currency, productFare.currency) &&
        Objects.equals(this.amount, productFare.amount) &&
        Objects.equals(this.vatPercent, productFare.vatPercent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, amount, vatPercent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductFare {\n");
    
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    vatPercent: ").append(toIndentedString(vatPercent)).append("\n");
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

