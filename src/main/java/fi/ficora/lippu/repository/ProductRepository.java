package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product, String> {


    List<Product> findByFromLatAndFromLon(Double fromLat, Double fromLon) ;
    List<Product> findByToLatAndToLon(Double toLat, Double toLon);
    List<Product> findByFromLatAndFromLonAndToLatAndToLon(Double fromLat,
                                                          Double fromLon,
                                                          Double toLat,
                                                          Double toLon) ;

    List<Product> findByFromId(String fromId) ;
    List<Product> findByToId(String toId) ;
    List<Product> findByFromIdAndToId(String fromId, String toId) ;

}
