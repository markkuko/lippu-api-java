package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Timetable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimetableRepository extends CrudRepository<Timetable, String> {

    Timetable findByProductId(String productId);
}
