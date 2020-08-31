package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import com.tw.locker.exceptions.UnrecognizedTicketException;
import com.tw.locker.exceptions.UsedTicketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LockerTests {

    private Locker locker;

    @BeforeEach
    void Init() {
        Integer capacity = 1;
        this.locker = new Locker("Test lockerId", capacity);
    }

    @Test
    void should_save_bag_and_return_a_ticket_when_save_bag_given_locker_has_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = locker.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
    }

    @Test
    void should_not_save_bag_and_return_error_message_when_save_bag_given_locker_has_no_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        locker.saveBag(bag);

        Integer newBagId = 2;
        Bag newBag = new Bag(newBagId);

        assertThrows(NoStorageException.class, () -> locker.saveBag(newBag));
    }

    @Test
    void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        Ticket ticket = locker.saveBag(bag);

        Bag actual = locker.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(bagId, actual.getId());
    }

    @Test
    void should_not_return_bag_and_return_invalid_error_when_take_bag_given_a_fake_ticket_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        locker.saveBag(bag);

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, "Test ticketId");

        assertThrows(FakeTicketException.class, () -> locker.takeBag(fakeTicket));
    }

    @Test
    void should_not_return_bag_and_return_used_error_when_take_bag_given_a_used_ticker_provided() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);
        Ticket ticket = locker.saveBag(bag);

        locker.takeBag(ticket);

        assertThrows(UsedTicketException.class, () -> locker.takeBag(ticket));
    }

    @Test
    void should_not_return_bag_and_return_unrecognized_error_when_take_bag_given_an_unrecognized_ticket_provided() {
        assertThrows(UnrecognizedTicketException.class, () -> locker.takeBag(null));
    }
}
