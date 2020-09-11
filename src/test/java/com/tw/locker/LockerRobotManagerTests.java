package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LockerRobotManagerTests {

    private LockerRobotManager lockerRobotManager;
    private final String testLockerId1 = "Test Locker Id 1";
    private final String testLockerId2 = "Test Locker Id 2";

    @BeforeEach
    void InitLockerRobotManager() {
        lockerRobotManager = new LockerRobotManager();
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_both_have_capacity() {
        LinkedList<Locker> lockers = buildLockers(2, 2);
        Locker expectedLocker = lockers.getFirst();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(expectedLocker.getLockerId(), actual.getLockerId());
        assertEquals(expectedLocker.takeBag(actual), bag);
    }

    @Test
    void should_save_bag_in_first_locker_and_return_ticket_when_save_bag_given_manage_two_lockers_and_first_no_capacity() {
        LinkedList<Locker> lockers = buildLockers(0, 1);
        Locker expectedLocker = lockers.getLast();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(actual.getLockerId(), expectedLocker.getLockerId());
        assertEquals(expectedLocker.takeBag(actual), bag);
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_lockers_and_both_are_full() {
        buildLockers(0, 0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_first_robot_when_save_bag_given_manage_two_robots_and_both_have_capacity() {
        LinkedList<LockerRobotBase> robots = buildRobots(1, 1);
        LockerRobotBase expectedRobot = robots.getFirst();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(expectedRobot.lockers.get(0).getLockerId(), actual.getLockerId());
        assertEquals(expectedRobot.takeBag(actual), bag);
    }

    @Test
    void should_saved_by_second_robot_when_save_bag_given_manage_two_robots_and_only_second_robot_have_capacity() {
        LinkedList<LockerRobotBase> robots = buildRobots(0, 1);
        LockerRobotBase expectedRobot = robots.getLast();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(expectedRobot.lockers.get(0).getLockerId(), actual.getLockerId());
        assertEquals(expectedRobot.takeBag(actual), bag);
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_two_robots_and_both_have_no_capacity() {
        buildRobots(0, 0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_saved_by_robot_when_save_bag_given_manage_one_robot_one_locker_and_both_have_capacity() {
        LinkedList<LockerRobotBase> robots = buildRobots(1);
        buildLockers(1);
        LockerRobotBase expectedRobot = robots.getFirst();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(expectedRobot.lockers.get(0).getLockerId(), actual.getLockerId());
        assertEquals(expectedRobot.takeBag(actual), bag);
    }

    @Test
    void should_save_in_locker_when_save_bag_given_manage_one_robot_one_locker_and_only_locker_have_capacity() {
        buildRobots(0);
        LinkedList<Locker> lockers = buildLockers(1);
        Locker expectedLocker = lockers.getFirst();
        Integer bagId = 1;
        Bag bag = new Bag(bagId);

        Ticket actual = lockerRobotManager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(bagId, actual.getBagId());
        assertEquals(expectedLocker.getLockerId(), actual.getLockerId());
        assertEquals(expectedLocker.takeBag(actual), bag);
    }

    @Test
    void should_not_save_bag_when_save_bag_given_manage_one_robot_one_locker_and_both_have_no_capacity() {
        buildRobots(0);
        buildLockers(0);
        Integer bagId = 1;

        assertThrows(NoStorageException.class, () -> lockerRobotManager.saveBag(new Bag(bagId)));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_two_lockers_only_and_valid_ticket_provided() {
        buildLockers(1, 1);
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
        buildLockers(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_two_robots_only_and_valid_ticket_provided() {
        buildRobots(1, 1);
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
        buildRobots(1, 1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    @Test
    void should_return_bag_when_take_bag_given_manage_one_robot_one_locker_and_valid_ticket_provided() {
        buildRobots(1);
        buildLockers(1);
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
        buildRobots(1);
        buildLockers(1);
        Integer bagId = 1;
        lockerRobotManager.saveBag(new Bag(bagId));

        Ticket fakeTicket = new Ticket("Fake Ticket Id", bagId, testLockerId1);

        assertThrows(FakeTicketException.class, () -> lockerRobotManager.takeBag(fakeTicket));
    }

    private LinkedList<Locker> buildLockers(int... capacities) {
        LinkedList<Locker> lockers = new LinkedList<>();

        for (int i = 1; i <= capacities.length; i++) {
            String lockerId = UUID.randomUUID().toString();
            lockers.add(new Locker(lockerId, capacities[i - 1]));
        }

        this.lockerRobotManager.setLockers(lockers);
        return lockers;
    }

    private LinkedList<LockerRobotBase> buildRobots(int... capacities) {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();

        for (int i = 1; i <= capacities.length; i++) {
            String lockerId = UUID.randomUUID().toString();
            Locker locker = new Locker(lockerId, capacities[i - 1]);

            LockerRobotBase robot = i % 2 == 1 ? new PrimaryLockerRobot() : new SmartLockerRobot();
            robot.setLockers(Collections.singletonList(locker));
            robots.add(robot);
        }

        this.lockerRobotManager.setRobots(robots);
        return robots;
    }
}
