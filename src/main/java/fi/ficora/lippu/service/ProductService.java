package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Product;
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


@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository repository;
    //private final DataRepository properties;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);


    public ProductService() {

    }
    public ProductList getAvailableProducts(LocalDate date) {

        ProductList productList = new ProductList();
        for (Product product : repository.findAll()) {
            ProductDescription productDescription= new ProductDescription()
                    .description(product.getDescription())
                    .contract(product.getContract())
                    .name(product.getName())
                    .validFrom(OffsetDateTime.now())
                    .validTo(OffsetDateTime.now().plusDays(1))
                    .suitablePassengerCategories(product.getSuitablePassengerCategories());
            productList.add(productDescription);
        }
        return productList;
    }

    public boolean isValidProduct(String productType, String contract) {
        for(Product productIter: repository.findAll()) {
            if(productIter.getProductType().equalsIgnoreCase(productType)
                    && productIter.getContract().equalsIgnoreCase(contract)) {
                return true;
            }
        }
        return false;
    }
}
