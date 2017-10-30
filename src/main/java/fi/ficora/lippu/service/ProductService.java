package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.Fare;
import fi.ficora.lippu.domain.Product;
import fi.ficora.lippu.domain.Transport;
import fi.ficora.lippu.domain.model.Accessibility;
import fi.ficora.lippu.domain.model.ProductDescription;
import fi.ficora.lippu.domain.model.ProductList;
import fi.ficora.lippu.domain.model.Travel;
import fi.ficora.lippu.repository.DataRepository;
import fi.ficora.lippu.repository.ProductRepository;
import fi.ficora.lippu.repository.TimetableRepository;
import fi.ficora.lippu.repository.TransportRepository;
import fi.ficora.lippu.util.ConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private ITimetableService timetableService;
    @Autowired
    private TransportRepository transportRepository;
    @Autowired
    private DataRepository dataRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);


    public ProductService() {

    }
    public ProductList getAvailableProductsTo(LocalDate date, Double toLat,
                                                Double toLon,
                                              List<Accessibility> accessibilities) {
        List<Product> products;
        if(date != null && toLon != null && toLat != null) {

            products = productRepository.findByToLatAndToLon(
                    toLat, toLon);
            if(accessibilities.size() > 0) {
                products = filterByAccessibilities(products,
                        accessibilities);
            }
            return checkProductTimetable(products, date);
        } else {
            return null;
        }
    }
    public ProductList getAvailableProductsFrom(LocalDate date, Double fromLat,
                                            Double fromLon,
                                                List<Accessibility> accessibilities) {
        List<Product> products;
        if(date != null && fromLon != null && fromLat != null) {

            products = productRepository.findByFromLatAndFromLon(
                    fromLat, fromLon);
            if(accessibilities.size() > 0) {
                products = filterByAccessibilities(products,
                        accessibilities);
            }
            return checkProductTimetable(products, date);
        } else {
            return null;
        }

    }
    public ProductList getAvailableProducts(LocalDate date, Double fromLat,
                                            Double fromLon, Double toLat,
                                            Double toLon,List<Accessibility> accessibilities) {
        List<Product> products;
        if(date != null && fromLon != null && fromLat != null &&
                toLat != null && toLon != null) {

            products = productRepository.findByFromLatAndFromLonAndToLatAndToLon(
                    fromLat, fromLon, toLat,toLon);
            if(accessibilities.size() > 0) {
                products = filterByAccessibilities(products,
                        accessibilities);
            }
            return checkProductTimetable(products, date);
        } else {
            return null;
        }

    }
    public ProductList getAvailableProducts(LocalDate date,
                                            List<Accessibility> accessibilities) {
        DayOfWeek day = date.getDayOfWeek();
        List<String> products = timetableService.getProductIdsOperateOnDay(day);
        ProductList productList = new ProductList();
        for(String productId: products) {
            Product product = productRepository.findOne(productId);
            // Check are there required accessibilities
            if(accessibilities.size() > 0) {
                if(hasRequiredAccessibityFeatures(product,
                        accessibilities)) {
                    productList.add(ConversionUtil.productToProductDescription(product,
                            timetableService.getProductDeparture(date, product)));
                }
            } else {
                productList.add(ConversionUtil.productToProductDescription(product,
                        timetableService.getProductDeparture(date, product)));
            }
        }
        return productList;
    }

    public boolean isValidProduct(String productType, String contract) {
        for(Product productIter: productRepository.findAll()) {
            if(productIter.getProductType().equalsIgnoreCase(productType)
                    && productIter.getContract().equalsIgnoreCase(contract)) {
                return true;
            }
        }
        return false;
    }


    public Fare getFare(String id) {
        // @todo replace stub implementation
        Fare fare = new Fare();
        fare.setAmount(14d);
        fare.setVat(10d);
        fare.setCurrency("EUR");
        fare.setProductId(id);
        return fare;
    }
    public Transport getTransport(String id) {
        // @todo replace stub implementation
        return transportRepository.findByProductId(id);

    }
    public Product getProduct(Travel travel, String contract) {
        List<Product> products = productRepository.
                findByFromLatAndFromLonAndToLatAndToLon(
                        travel.getFrom().getLat(),
                        travel.getFrom().getLon(),
                        travel.getTo().getLat(),
                        travel.getTo().getLon());
        List<Product> validProducts=
                checkProductTimetable2(products,travel.getDateTime());
        // @todo handle case where multiple product found
        if(validProducts.size() == 1) {
            return validProducts.get(0);
        } else {
            return null;
        }

    }
    public List<String> getPassengerCategories() {

        return dataRepository.getPassengerCategories();
    }
    private ProductList checkProductTimetable(List<Product> products,
                                              LocalDate date) {
        ProductList productList = new ProductList();
        for(Product product: products) {
            if(timetableService.doesProductOperateOn(date, product)) {
                productList.add(ConversionUtil.productToProductDescription(product,
                        timetableService.getProductDeparture(date,product)));
            }

        }
        return productList;
    }
    private List<Product> checkProductTimetable2(List<Product> products,
                                              OffsetDateTime date) {
        List<Product> productList = new ArrayList<>();
        for(Product product: products) {
            if(timetableService.hasProductDepartures(date, product)) {
                productList.add(product);
            }

        }
        return productList;
    }

    private List<Product> filterByAccessibilities(List<Product> products,
                                                  List<Accessibility> accessibilities) {

        List<Product> returnProducts = new ArrayList<>();
        for(Product product: products) {
            if(hasRequiredAccessibityFeatures(
                    product, accessibilities)) {
                returnProducts.add(product);
            }
        }
        return returnProducts;
    }
    private boolean hasRequiredAccessibityFeatures(Product product,
                                                   List<Accessibility> accessibilities) {
        boolean foundAll = true;
        for (Accessibility accessibility1 : accessibilities) {
            boolean foundAccessibility = false;
            for(Accessibility accessibility : product.getAccessibilities()) {
                if(accessibility.getTitle().compareTo(
                        accessibility1.getTitle()) == 0) {
                    log.debug("Found accessibility:{}",
                            accessibility.getTitle());
                    foundAccessibility = true;
                    break;
                }
            }
            if(!foundAccessibility) {
                log.debug("Did not find accessibility {} in the product {}",
                        accessibility1.getTitle(),
                        product.getId()
                );
                foundAll = false;
                break;
            }
        }
        if(foundAll) {
            return true;
        } else {
            return false;
        }
    }
}
