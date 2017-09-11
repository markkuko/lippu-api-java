package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

}
