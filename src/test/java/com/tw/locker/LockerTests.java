package com.tw.locker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LockerTests {

    @Test
    public void should_save_bag_and_return_a_ticket_when_save_bag_given_locker_has_storage() {
        Locker locker = new Locker();
        Integer bagId = 13;
        Bag bag = new Bag(bagId);

        SaveBagResponse actual = locker.saveBag(bag);

        String  ticketId = actual.getTicket().getId();
        Ticket ticket = new Ticket(ticketId, bagId);
        SaveBagResponse expected = new SaveBagResponse(true, "Save bag successfully.", ticket);

        assertEquals(expected.getIsSuccess(), actual.getIsSuccess());
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getTicket().getBagId(), actual.getTicket().getBagId());
        assertEquals(expected.getTicket().getId(), actual.getTicket().getId());
    }
}
