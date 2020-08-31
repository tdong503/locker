package com.tw.locker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrimaryLockerRobotTests {

    private PrimaryLockerRobot primaryLockerRobot;

    @BeforeEach
    void Init() {
        LinkedList<Locker> lockers = new LinkedList<>();
        Integer capacity = 2;
        lockers.add(new Locker("Test ticketId 1", capacity));
        lockers.add(new Locker("Test ticketId 2", capacity));
        this.primaryLockerRobot = new PrimaryLockerRobot(lockers);
    }

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_every_locker_has_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = primaryLockerRobot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals("Test ticketId 1", actual.getLockerId());
    }
}
