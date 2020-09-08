package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;
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

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_locker_and_both_are_full() {
        InitManagedLockers(0,0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_first_robot_when_save_bag_given_manage_two_robots_and_both_have_capacity() {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();

        LinkedList<Locker> lockers1 = new LinkedList<>();
        lockers1.add(new Locker(testLockerId1, 1));
        robots.add(new PrimaryLockerRobot(lockers1));

        LinkedList<Locker> lockers2 = new LinkedList<>();
        lockers2.add(new Locker(testLockerId2, 1));
        robots.add(new SmartLockerRobot(lockers2));

        this.lockerRobotManager = new LockerRobotManager(null, robots);

        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    private void InitManagedLockers(int firstLockerCapacity , int secondLockerCapacity) {
        LinkedList<Locker> lockers = new LinkedList<>();
        lockers.add(new Locker(testLockerId1, firstLockerCapacity));
        lockers.add(new Locker(testLockerId2, secondLockerCapacity));
        this.lockerRobotManager = new LockerRobotManager(lockers, null);
    }
}
