package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Capasity;
import fi.ficora.lippu.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CapasityRepository extends CrudRepository<Capasity, String> {


    Capasity findOneByProductId(String productId);

}
