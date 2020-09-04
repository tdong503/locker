package com.tw.locker;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SmartLockerRobotTests {

    private SmartLockerRobot smartLockerRobot;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";



    @Test
    void should_save_bag_in_first_locker_and_return_a_ticket_when_save_bag_given_first_locker_capacity_is_larger_than_second() {
        InitManagedLockers(2, 1);
        Integer bagId = 1;
        Ticket actual = smartLockerRobot.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
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
