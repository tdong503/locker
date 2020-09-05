package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SmartLockerRobotTests {

    private SmartLockerRobot smartLockerRobot;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_first_locker_free_capacity_is_larger_than_second() {
        InitManagedLockers(2, 1);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_in_second_locker_and_return_a_ticket_when_save_bag_given_second_locker_free_capacity_is_larger_than_first_locker() {
        InitManagedLockers(1, 2);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_two_lockers_has_same_free_capacity() {
        InitManagedLockers(1, 1);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_into_second_locker_and_return_a_ticket_when_save_bag_given_two_lockers_and_only_first_locker_no_capacity() {
        InitManagedLockers(1, 1);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket actual = smartLockerRobot.saveBag(new Bag(newBagId));

        assertNotNull(actual);
        assertEquals(newBagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        InitManagedLockers(2, 2);
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = smartLockerRobot.saveBag(new Bag(newBagId));

        Bag actual = smartLockerRobot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    private void InitManagedLockers(int firstLockerCapacity , int secondLockerCapacity) {
        LinkedList<Locker> lockers = new LinkedList<>();
        lockers.add(new Locker(testLockerId1, firstLockerCapacity));
        lockers.add(new Locker(testLockerId2, secondLockerCapacity));
        this.smartLockerRobot = new SmartLockerRobot(lockers);
    }
}
