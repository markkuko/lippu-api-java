package fi.ficora.lippu.repository;

import fi.ficora.lippu.domain.Reservation;
import fi.ficora.lippu.domain.ReservationItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Repository
public interface ReservationItemRepository extends CrudRepository<ReservationItem, String>{
    public List<ReservationItem> findByProductIdAndTravelDateBetween(String productId,
                                                                     LocalDateTime from, LocalDateTime to);

    public long count();
    public ReservationItem findOneByReservationData(String reservationData);
    public List<ReservationItem> findAllByCaseId(String caseId);

    public List<ReservationItem> findAllByConfirmedAndReservationValidToBetween(boolean confirmed,
                                                                                OffsetDateTime from,
                                                                                OffsetDateTime to);

}
