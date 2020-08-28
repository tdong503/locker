package com.tw.locker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.tw.locker.Messages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        assertEquals(SAVE_BAG_SUCCESSFULLY, actual.getMessage());
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
        assertEquals(NO_STORAGE, actual.getMessage());
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
        assertEquals(TAKE_BAG_SUCCESSFULLY, actual.getMessage());
        assertEquals(bagId, actual.getBag().getId());
    }

    @Test
    public void should_not_return_bag_and_return_invalid_error_when_take_bag_given_a_fake_ticket_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        locker.saveBag(bag);

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId);

        TakeBagResponse actual = locker.takeBag(fakeTicket);

        assertEquals(false, actual.getIsSuccess());
        assertEquals(FAKE_TICKET, actual.getMessage());
        assertNull(actual.getBag());
    }

    @Test
    public void should_not_return_bag_and_return_used_error_when_take_bag_given_a_used_ticker_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        SaveBagResponse response = locker.saveBag(bag);

        Ticket ticket = response.getTicket();
        locker.takeBag(ticket);

        TakeBagResponse actual = locker.takeBag(ticket);

        assertEquals(false, actual.getIsSuccess());
        assertEquals(USED_TICKET, actual.getMessage());
        assertNull(actual.getBag());
    }

    @Test
    public void should_not_return_bag_and_return_unrecognized_error_when_take_bag_given_an_unrecognized_ticket_provided() {
        TakeBagResponse actual = locker.takeBag(null);

        assertEquals(false, actual.getIsSuccess());
        assertEquals(UNRECOGNIZED_TICKET, actual.getMessage());
        assertNull(actual.getBag());
    }
}
