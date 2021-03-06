package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import com.tw.locker.exceptions.UnrecognizedTicketException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SmartLockerRobotTests {

    private final LockerRobotBase smartLockerRobot = new SmartLockerRobot();

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_first_locker_free_capacity_is_larger_than_second() {
        initManagedLockers(2, 1);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, smartLockerRobot.takeBag(actual).getId());
    }

    @Test
    void should_save_bag_in_second_locker_and_return_a_ticket_when_save_bag_given_second_locker_free_capacity_is_larger_than_first_locker() {
        initManagedLockers(1, 2);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, smartLockerRobot.takeBag(actual).getId());
    }

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_two_lockers_has_same_free_capacity() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, smartLockerRobot.takeBag(actual).getId());
    }

    @Test
    void should_save_bag_into_second_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_only_first_locker_no_capacity() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket actual = smartLockerRobot.saveBag(new Bag(newBagId));

        assertNotNull(actual);
        assertEquals(newBagId, smartLockerRobot.takeBag(actual).getId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_two_lockers_and_every_locker_has_no_capacity() {
        initManagedLockers(1, 1);
        for (int bagId = 1; bagId <= 2; bagId++) {
            smartLockerRobot.saveBag(new Bag(bagId));
        }

        Integer newBagId = 3;
        Bag newBag = new Bag(newBagId);

        assertThrows(NoStorageException.class, () -> smartLockerRobot.saveBag(newBag));
    }

    @Test
    void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        initManagedLockers(2, 2);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = smartLockerRobot.saveBag(new Bag(newBagId));

        Bag actual = smartLockerRobot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    @Test
    void should_not_return_bag_and_return_fake_error_when_take_bag_given_a_fake_ticket_and_valid_locker_id_provided() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, "Test Locker Id 1");

        assertThrows(FakeTicketException.class, () -> smartLockerRobot.takeBag(fakeTicket));
    }

    @Test
    void should_not_return_bag_and_return_fake_error_when_take_bag_given_a_fake_ticket_provided_and_invalid_locker() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, "Fake Locker Id");

        assertThrows(FakeTicketException.class, () -> smartLockerRobot.takeBag(fakeTicket));
    }

    @Test
    void should_not_return_bag_and_return_unrecognized_error_when_take_bag_given_an_unrecognized_ticket_provided() {
        initManagedLockers(1, 1);

        assertThrows(UnrecognizedTicketException.class, () -> smartLockerRobot.takeBag(null));
    }

    private void initManagedLockers(int firstLockerCapacity , int secondLockerCapacity) {
        LinkedList<Locker> lockers = new LinkedList<>();
        String testLockerId1 = "Test Locker Id 1";
        lockers.add(new Locker(testLockerId1, firstLockerCapacity));
        String testLockerId2 = "Test Locker Id 2";
        lockers.add(new Locker(testLockerId2, secondLockerCapacity));
        this.smartLockerRobot.setLockers(lockers);
    }
}
