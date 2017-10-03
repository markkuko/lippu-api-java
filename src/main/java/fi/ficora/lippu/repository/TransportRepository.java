package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Transport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends CrudRepository<Transport, String> {

    Transport findByProductId(String productId);
}
