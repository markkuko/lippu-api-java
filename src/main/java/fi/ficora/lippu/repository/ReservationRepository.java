package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String>{

}
