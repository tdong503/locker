package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class LockerRobotManagerTests {

    private LockerRobotManager lockerRobotManager;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @BeforeEach
    void InitLockerRobotManager() {
        lockerRobotManager = new LockerRobotManager();
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_both_have_capacity() {
        initManagedLockers(2, 2);
        Integer bagId = 1;

        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId1, actual.getLockerId());
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_first_no_capacity() {
        initManagedLockers(0, 1);
        Integer bagId = 1;

        Ticket actual = lockerRobotManager.saveBag(new Bag(bagId));

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(testLockerId2, actual.getLockerId());
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_lockers_and_both_are_full() {
        initManagedLockers(0, 0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_first_robot_when_save_bag_given_manage_two_robots_and_both_have_capacity() {
        initManagedRobots(1, 1);

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
        initManagedRobots(0, 0);
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
        initManagedRobotsAndLockers(0, 0);
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
        initLockerRobotManager(1, 1, robotsCapacity, lockersCapacity);
    }

    private void initManagedRobots(int firstRobotCapacity, int secondRobotCapacity) {
        initLockerRobotManager(2, 0, firstRobotCapacity, secondRobotCapacity);
    }

    private void initManagedLockers(int firstLockerCapacity, int secondLockerCapacity) {
        initLockerRobotManager(0, 2, firstLockerCapacity, secondLockerCapacity);
    }

    private void initLockerRobotManager(int robotCount, int lockerCont, int... capacities) {
        LinkedList<LockerRobotBase> robots = buildRobots(robotCount);
        LinkedList<Locker> lockers = buildLockers(capacities);
        LinkedList<Locker> managedLockers = new LinkedList<>();

        for (int i = 1; i <= robotCount; i++) {
            LinkedList<Locker> lockersTemp = new LinkedList<>();
            lockersTemp.add(lockers.get(i - 1));

            robots.get(i - 1).setLockers(lockersTemp);
        }

        for (int i = robotCount + 1; i <= robotCount + lockerCont; i++) {
            managedLockers.add(lockers.get(i - 1));
        }

        if (robotCount > 0) {
            this.lockerRobotManager.setRobots(robots);
        }

        if (lockerCont > 0) {
            this.lockerRobotManager.setLockers(managedLockers);
        }
    }

    private LinkedList<Locker> buildLockers(int... capacities) {
        LinkedList<Locker> lockers = new LinkedList<>();

        for (int i = 1; i <= capacities.length; i++) {
            lockers.add(new Locker("Test Locker Id " + i, capacities[i - 1]));
        }
        return lockers;
    }

    private LinkedList<LockerRobotBase> buildRobots(int robotCount) {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();

        for (int i = 1; i <= robotCount; i++) {
            LockerRobotBase robotBase = i % 2 == 1 ? new PrimaryLockerRobot() : new SmartLockerRobot();
            robots.add(robotBase);
        }

        return robots;
    }
}
