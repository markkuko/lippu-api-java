package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Nonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NonceRepository extends CrudRepository<Nonce, String> {
    Nonce findOneNonceByClient(String client);
    List<Nonce> findByExpBetween(LocalDateTime start, LocalDateTime stop);
}
