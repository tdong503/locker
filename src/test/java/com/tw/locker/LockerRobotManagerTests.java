package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class LockerRobotManagerTests {

    private LockerRobotManager lockerRobotManager;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_both_have_capacity() {
        initManagedLockers(2,2);
        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_first_no_capacity() {
        initManagedLockers(0,1);
        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_lockers_and_both_are_full() {
        initManagedLockers(0,0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_first_robot_when_save_bag_given_manage_two_robots_and_both_have_capacity() {
        initManagedRobots(1,1);

        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_saved_by_second_robot_when_save_bag_given_manage_two_robots_and_only_second_robot_have_capacity() {
        initManagedRobots(0, 1);

        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_robots_and_both_have_no_capacity() {
        initManagedRobots(0,0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_robot_when_save_bag_given_manage_one_robot_one_locker_and_both_have_capacity() {
        initManagedRobotsAndLockers(1, 1);

        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_in_locker_when_save_bag_given_manage_one_robot_one_locker_and_only_locker_have_capacity() {
        initManagedRobotsAndLockers(0, 1);

        Integer bagId = 1;
        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_one_robot_one_locker_and_both_have_no_capacity() {
        initManagedRobotsAndLockers(0,0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_two_lockers_only_and_valid_ticket_provided() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = lockerRobotManager.saveBag(new Bag(newBagId));

        Bag actual = lockerRobotManager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());

    }

    @Test
    void should_not_return_bag_and_throw_no_storage_when_take_bag_given_manage_two_lockers_only_and_invalid_ticket_provided() {
        initManagedLockers(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_two_robots_only_and_valid_ticket_provided() {
        initManagedRobots(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = lockerRobotManager.saveBag(new Bag(newBagId));

        Bag actual = lockerRobotManager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    @Test
    void should_not_return_bag_and_throw_no_storage_when_take_bag_given_manage_two_robots_only_and_invalid_ticket_provided() {
        initManagedRobots(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_one_robot_one_locker_and_valid_ticket_provided() {
        initManagedRobotsAndLockers(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = lockerRobotManager.saveBag(new Bag(newBagId));

        Bag actual = lockerRobotManager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    @Test
    void should_not_return_bag_and_throw_no_storage_when_take_bag_given_manage_one_robot_one_locker_and_invalid_ticket_provided() {
        initManagedRobotsAndLockers(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    private void initManagedRobotsAndLockers(int robotsCapacity, int lockersCapacity) {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();
        LinkedList<Locker> lockers1 = new LinkedList<>();
        lockers1.add(new Locker(testLockerId1, robotsCapacity));
        robots.add(new PrimaryLockerRobot(lockers1));

        LinkedList<Locker> lockers = new LinkedList<>();
        lockers.add(new Locker(testLockerId2, lockersCapacity));

        this.lockerRobotManager = new LockerRobotManager(lockers, robots);
    }

    private void initManagedRobots(int firstRobotCapacity, int secondRobotCapacity) {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();

        LinkedList<Locker> lockers1 = new LinkedList<>();
        lockers1.add(new Locker(testLockerId1, firstRobotCapacity));
        robots.add(new PrimaryLockerRobot(lockers1));

        LinkedList<Locker> lockers2 = new LinkedList<>();
        lockers2.add(new Locker(testLockerId2, secondRobotCapacity));
        robots.add(new SmartLockerRobot(lockers2));

        this.lockerRobotManager = new LockerRobotManager(null, robots);
    }

    private void initManagedLockers(int firstLockerCapacity , int secondLockerCapacity) {
        LinkedList<Locker> lockers = new LinkedList<>();
        lockers.add(new Locker(testLockerId1, firstLockerCapacity));
        lockers.add(new Locker(testLockerId2, secondLockerCapacity));
        this.lockerRobotManager = new LockerRobotManager(lockers, null);
    }
}
