package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Capacity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CapacityRepository extends CrudRepository<Capacity, String> {


    Capacity findOneByProductId(String productId);

}
