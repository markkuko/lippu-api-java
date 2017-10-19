package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.ReservationItem;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for generating ticket payloads.
 * @author markkuko
 */

@Service
public class TicketService implements ITicketService{


    public String generateTicket(ReservationItem item) {
        // @todo replace stub implementation
        return UUID.randomUUID().toString();
    }


}
