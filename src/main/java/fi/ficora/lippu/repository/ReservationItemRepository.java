package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.ReservationItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Repository
public interface ReservationItemRepository extends CrudRepository<ReservationItem, String>{
    List<ReservationItem> findByProductIdAndTravelDateBetween(String productId,
                                                              LocalDateTime from, LocalDateTime to);

    long count();
    ReservationItem findOneByTravelEntitlementId(String travelEntitlementId);
    List<ReservationItem> findAllByCaseId(String caseId);

    List<ReservationItem> findAllByConfirmedAndReservationValidToBetween(boolean confirmed,
                                                                         OffsetDateTime from,
                                                                         OffsetDateTime to);

}
