package fi.ficora.lippu.service;

import fi.ficora.lippu.domain.ReservationItem;

import java.util.UUID;

/**
 * Service for generating ticket payloads.
 * @author markkuko
 */


public interface ITicketService {
    /**
     * Generates string representation of the ticket. Currently is only
     * a STUB implementation, NOT validable by ticket machines.
     * @return String represenatation of the ticket
     */
    public String generateTicket(ReservationItem item);


}
