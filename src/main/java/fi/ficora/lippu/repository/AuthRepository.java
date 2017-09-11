package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Nonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Nonce, String> {
    Nonce findOneNonceByClient(String client);

}
