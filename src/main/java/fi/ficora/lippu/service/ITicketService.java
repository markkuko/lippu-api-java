package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.ReservationItem;

/**
 * Service for generating ticket payloads.
 * @author markkuko
 */


public interface ITicketService {
    /**
     * Generates string representation of the ticket. Currently is only
     * a STUB implementation, NOT used by any validation machines.
     * @return String representation of the ticket
     */
    String generateTicket(ReservationItem item);


}
