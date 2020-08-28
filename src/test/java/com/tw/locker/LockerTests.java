package com.tw.locker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tw.locker.Messages.NOSTORAGE;
import static com.tw.locker.Messages.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class LockerTests {

    private Locker locker;

    @Autowired
    public LockerTests(Locker locker) {
        this.locker = locker;
    }

    @Test
    public void should_save_bag_and_return_a_ticket_when_save_bag_given_locker_has_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        SaveBagResponse actual = locker.saveBag(bag);

        assertEquals(true, actual.getIsSuccess());
        assertEquals(SUCCESS, actual.getMessage());
        assertEquals(bagId, actual.getTicket().getBagId());
    }

    @Test
    public void should_not_save_bag_and_return_error_message_when_save_bag_given_locker_has_no_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        locker.saveBag(bag);

        Integer newBagId = 2;
        Bag newBag = new Bag(newBagId);

        SaveBagResponse actual = locker.saveBag(newBag);

        assertEquals(false, actual.getIsSuccess());
        assertEquals(NOSTORAGE, actual.getMessage());
        assertNull(actual.getTicket());
    }

    @Test
    public void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        SaveBagResponse response = locker.saveBag(bag);

        Ticket ticket = response.getTicket();

        TakeBagResponse actual = locker.takeBag(ticket);

        assertEquals(true, actual.getIsSuccess());
        assertEquals("Take Bag Successfully.", actual.getMessage());
        assertEquals(bagId, actual.getBag().getId());
    }
}
