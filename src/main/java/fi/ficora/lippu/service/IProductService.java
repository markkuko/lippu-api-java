package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.model.ProductDescription;
import fi.ficora.lippu.domain.model.ProductList;
import fi.ficora.lippu.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
     * Checks if there is a product with the given parameters.
     * @param productType Product type
     * @param contract Identification string for contract.
     * @return If product was found for the given parameters.
     */
    public boolean isValidProduct(String productType, String contract) ;

}
