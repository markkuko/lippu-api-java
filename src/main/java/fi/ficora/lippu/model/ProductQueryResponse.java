package fi.ficora.lippu.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fi.ficora.lippu.model.ProductList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProductQueryResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-05T15:09:31.417+03:00")

public class ProductQueryResponse   {
  @JsonProperty("products")
  private ProductList products = null;

  @JsonProperty("passengerCategories")
  @Valid
  private List<String> passengerCategories = new ArrayList<String>();

  public ProductQueryResponse products(ProductList products) {
    this.products = products;
    return this;
  }

   /**
   * Get products
   * @return products
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ProductList getProducts() {
    return products;
  }

  public void setProducts(ProductList products) {
    this.products = products;
  }

  public ProductQueryResponse passengerCategories(List<String> passengerCategories) {
    this.passengerCategories = passengerCategories;
    return this;
  }

  public ProductQueryResponse addPassengerCategoriesItem(String passengerCategoriesItem) {
    this.passengerCategories.add(passengerCategoriesItem);
    return this;
  }

   /**
   * Get passengerCategories
   * @return passengerCategories
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getPassengerCategories() {
    return passengerCategories;
  }

  public void setPassengerCategories(List<String> passengerCategories) {
    this.passengerCategories = passengerCategories;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductQueryResponse productQueryResponse = (ProductQueryResponse) o;
    return Objects.equals(this.products, productQueryResponse.products) &&
        Objects.equals(this.passengerCategories, productQueryResponse.passengerCategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products, passengerCategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductQueryResponse {\n");
    
    sb.append("    products: ").append(toIndentedString(products)).append("\n");
    sb.append("    passengerCategories: ").append(toIndentedString(passengerCategories)).append("\n");
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

