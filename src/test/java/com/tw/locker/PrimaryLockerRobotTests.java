package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryLockerRobotTests {

    private PrimaryLockerRobot primaryLockerRobot;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @BeforeEach
    void Init() {
        LinkedList<Locker> lockers = new LinkedList<>();
        Integer capacity = 2;
        lockers.add(new Locker(testLockerId1, capacity));
        lockers.add(new Locker(testLockerId2, capacity));
        this.primaryLockerRobot = new PrimaryLockerRobot(lockers);
    }

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_every_locker_has_storage() {
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = primaryLockerRobot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_into_second_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_only_first_locker_no_storage() {
        for (int bagId = 1; bagId <= 2; bagId++) {
            Bag bag = new Bag(bagId);
            primaryLockerRobot.saveBag(bag);
        }

        Integer newBagId = 3;
        Bag newBag = new Bag(newBagId);
        Ticket actual = primaryLockerRobot.saveBag(newBag);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_two_lockers_and_every_locker_has_no_storage() {
        for (int bagId = 1; bagId <= 4; bagId++) {
            Bag bag = new Bag(bagId);
            primaryLockerRobot.saveBag(bag);
        }

        Integer newBagId = 5;
        Bag newBag = new Bag(newBagId);

        assertThrows(NoStorageException.class, () -> primaryLockerRobot.saveBag(newBag));
    }
}
