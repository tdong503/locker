package com.tw.locker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.Lock;

import static com.tw.locker.Messages.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
