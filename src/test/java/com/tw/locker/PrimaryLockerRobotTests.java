package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import com.tw.locker.exceptions.UnrecognizedTicketException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryLockerRobotTests {

    private RobotBase primaryLockerRobot;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_every_locker_has_capacity() {
        InitManagedLockers();
        Integer bagId = 1;
        Ticket actual = primaryLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_into_second_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_only_first_locker_no_capacity() {
        InitManagedLockers();
        for (int bagId = 1; bagId <= 2; bagId++) {
            primaryLockerRobot.saveBag(new Bag(bagId));
        }

        Integer newBagId = 3;
        Ticket actual = primaryLockerRobot.saveBag(new Bag(newBagId));

        assertNotNull(actual);
        assertEquals(newBagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_two_lockers_and_every_locker_has_no_capacity() {
        InitManagedLockers();
        for (int bagId = 1; bagId <= 4; bagId++) {
            primaryLockerRobot.saveBag(new Bag(bagId));
        }

        Integer newBagId = 5;
        Bag newBag = new Bag(newBagId);

        assertThrows(NoStorageException.class, () -> primaryLockerRobot.saveBag(newBag));
    }

    @Test
    void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        InitManagedLockers();
        Integer bagId = 1;
        primaryLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = primaryLockerRobot.saveBag(new Bag(newBagId));

        Bag actual = primaryLockerRobot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    @Test
    void should_not_return_bag_and_return_fake_error_when_take_bag_given_a_fake_ticket_and_valid_locker_id_provided() {
        InitManagedLockers();
        Integer bagId = 1;
        primaryLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        primaryLockerRobot.saveBag(new Bag(newBagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> primaryLockerRobot.takeBag(fakeTicket));
    }

    @Test
    void should_not_return_bag_and_return_fake_error_when_take_bag_given_a_fake_ticket_provided_and_invalid_locker() {
        InitManagedLockers();
        Integer bagId = 1;
        primaryLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        primaryLockerRobot.saveBag(new Bag(newBagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, "Fake Locker Id");

        assertThrows(FakeTicketException.class, () -> primaryLockerRobot.takeBag(fakeTicket));
    }

    @Test
    void should_not_return_bag_and_return_unrecognized_error_when_take_bag_given_an_unrecognized_ticket_provided() {
        InitManagedLockers();

        assertThrows(UnrecognizedTicketException.class, () -> primaryLockerRobot.takeBag(null));
    }

    private void InitManagedLockers() {
        LinkedList<Locker> lockers = new LinkedList<>();
        Integer capacity = 2;
        lockers.add(new Locker(testLockerId1, capacity));
        lockers.add(new Locker(testLockerId2, capacity));
        this.primaryLockerRobot = new PrimaryLockerRobot(lockers);
    }
}
