package fi.ficora.lippu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fi.ficora.lippu.domain.Client;


@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

}
