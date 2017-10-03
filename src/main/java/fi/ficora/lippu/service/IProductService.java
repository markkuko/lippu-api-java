package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Fare;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Transport;
import fi.ficora.lippu.domain.model.ProductDescription;
import fi.ficora.lippu.domain.model.ProductList;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Interface declaration for Product functionality.
 * @author markkuko
 */
public interface IProductService {

    /**
     * Get available products for the given date.
     * @param date  The date to search products.
     * @return List of products available.
     */
    public ProductList getAvailableProducts(LocalDate date) ;

    /**
     * Get available products for the given date and transport
     * departure coordinate.
     *
     * @param date  The date the products operate on
     * @param fromLat Latitude part of the departure coordinate
     * @param fromLon Longitude part of the departure coordinate
     * @return List of products available for the date and departure coordinate.
     */

    public ProductList getAvailableProductsFrom(LocalDate date, Double fromLat,
                                                Double fromLon);
    /**
     * Get available products for the given date and transport
     * destination coordinate.
     *
     * @param date  The date the products operate on
     * @param toLat Latitude part of the destination coordinate
     * @param toLon Longitude part of the destination coordinate
     * @return List of products available for the date and destination coordinate.
     */

    public ProductList getAvailableProductsTo(LocalDate date, Double toLat,
                                              Double toLon);
    /**
     * Get available products for the given date, departure coordinates
     * and destination coordinates.
     *
     * @param date  The date the products operate on
     * @param fromLat Latitude part of the departure coordinate
     * @param fromLon Longitude part of the departure coordinate
     * @param toLat Latitude part of the destination coordinate
     * @param toLon Longitude part of the destination coordinate
     * @return List of products available for the date, departure and
     * destination coordinates.
     */
    public ProductList getAvailableProducts(LocalDate date, Double fromLat,
                                            Double fromLon, Double toLat,
                                            Double toLon);
    /**
     * Checks if there is a product with the given parameters.
     * @param productType Product type
     * @param contract Identification string for contract.
     * @return If product was found for the given parameters.
     */
    public boolean isValidProduct(String productType, String contract) ;

    /**
     * Get fare for a product or service.
     *
     * @param id Product or service id to locate fare price.
     * @return Fare information for the product.
     */
    public Fare getFare(String id);
    /**
     * Get transport for a product.
     * @param id Product íd to find transport
     * @return Transport for the product
     */
    public Transport getTransport(String id);

    /**
     * Retrieves all passenger categories.
     * @return List of passenger categories.
     */
    public List<String> getPassengerCategories();

    /**
     * Retrieves product for the travel departure, destination
     * and travel date.
     * @param travel Travel information, departure and destination and travel date.
     * @param contract Contract for operation
     * @return Product matching the parameters or null.
     */
    public Product getProduct(Travel travel, String contract);
}
