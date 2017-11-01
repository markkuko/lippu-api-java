package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Fare;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Transport;
import fi.ficora.lippu.domain.model.Accessibility;
import fi.ficora.lippu.domain.model.ExtraService;
import fi.ficora.lippu.domain.model.ProductList;
import fi.ficora.lippu.domain.model.Travel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface declaration for Product functionality.
 * @author markkuko
 */
public interface IProductService {

    /**
     * Get available products for the given date.
     * @param date  The date to search products.
     * @param accessiblity List of {@link Accessibility} features,
     *                     that are required by the product.
     * @return List of products available.
     */
    ProductList getAvailableProducts(LocalDate date,
                                     List<Accessibility> accessiblity) ;

    /**
     * Get available products for the given date and transport
     * departure coordinate.
     *
     * @param date  The date the products operate on
     * @param fromLat Latitude part of the departure coordinate
     * @param fromLon Longitude part of the departure coordinate
     * @param accessiblity List of {@link Accessibility} features,
     *                     that are required by the product.
     * @return List of products available for the date and departure coordinate.
     */

    ProductList getAvailableProductsFrom(LocalDate date, Double fromLat,
                                         Double fromLon,
                                         List<Accessibility> accessiblity);
    /**
     * Get available products for the given date and transport
     * destination coordinate.
     *
     * @param date  The date the products operate on
     * @param toLat Latitude part of the destination coordinate
     * @param toLon Longitude part of the destination coordinate
     * @param accessiblity List of {@link Accessibility} features,
     *                     that are required by the product.
     * @return List of products available for the date and destination coordinate.
     */

    ProductList getAvailableProductsTo(LocalDate date, Double toLat,
                                       Double toLon,
                                       List<Accessibility> accessiblity);
    /**
     * Get available products for the given date, departure coordinates
     * and destination coordinates.
     *
     * @param date  The date the products operate on
     * @param fromLat Latitude part of the departure coordinate
     * @param fromLon Longitude part of the departure coordinate
     * @param toLat Latitude part of the destination coordinate
     * @param toLon Longitude part of the destination coordinate
     * @param accessiblity List of {@link Accessibility} features,
     *                     that are required by the product.
     * @return List of products available for the date, departure and
     * destination coordinates.
     */
    ProductList getAvailableProducts(LocalDate date, Double fromLat,
                                     Double fromLon, Double toLat,
                                     Double toLon,
                                     List<Accessibility> accessiblity);
    /**
     * Checks if there is a product with the given parameters.
     * @param productType Product type
     * @param contract Identification string for contract.
     * @return If product was found for the given parameters.
     */
    boolean isValidProduct(String productType, String contract) ;

    /**
     * Get fare for a product or service.
     *
     * @param id Product or service id to locate fare price.
     * @return Fare information for the product.
     */
    Fare getFare(String id);
    /**
     * Get transport for a product.
     * @param id Product íd to find transport
     * @return Transport for the product
     */
    Transport getTransport(String id);

    /**
     * Retrieves all passenger categories.
     * @return List of passenger categories.
     */
    List<String> getPassengerCategories();

    /**
     * Retrieves product for the travel departure, destination
     * and travel date.
     * @param travel Travel information, departure and destination and travel date.
     * @param contract Contract for operation
     * @return Product matching the parameters or null.
     */
    Product getProduct(Travel travel, String contract);

    /**
     * Checks if product has all the required accessibility
     * features required by the parameter accessibilities.
     * @param product Product for which the accessibility feature check is made.
     * @param accessibilities List of required accessibility features for the product
     * @return True if product has all the required accessibility features, otherwise
     * false.
     */
    boolean hasRequiredAccessibityFeatures(Product product,
                                                  List<Accessibility> accessibilities);

    /**
     * Search certain {@link Accessibility}  from the products accessibilities.
     * The accessibility is given as the title attribute to the method.
     * @param product Product from which the accessibility feature is searched.
     * @param title List of required Accessibility features for the product
     * @return The given {@link Accessibility} object from the product or null if
     * not found.
     */
    Accessibility getAccessibilityFromProduct(Product product,
                                                    Accessibility.TitleEnum title);

    /**
     * Search certain {@link ExtraService} from the products extraServices. The ExtraService's
     * title is given as the title attribute to the method.
     * @param product Product for which the ExtraService feature check is made.
     * @param title The title for the ExtraService to search from the product.
     * @return The {@link ExtraService} object from the product or null if
     * not found.
     */
    ExtraService getExtraServiceFromProduct(Product product, String title);
}
