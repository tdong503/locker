package com.tw.locker;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class LockerRobotManagerTests {

    private LockerRobotManager lockerRobotManager;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_locker_and_both_have_capacity() {
        InitManagedLockers(2,2);
        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_locker_and_second_has_capacity_but_first_dose_not() {
        InitManagedLockers(0,1);
        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    private void InitManagedLockers(int firstLockerCapacity , int secondLockerCapacity) {
        LinkedList<Locker> lockers = new LinkedList<>();
        lockers.add(new Locker(testLockerId1, firstLockerCapacity));
        lockers.add(new Locker(testLockerId2, secondLockerCapacity));
        this.lockerRobotManager = new LockerRobotManager(lockers);
    }
}
