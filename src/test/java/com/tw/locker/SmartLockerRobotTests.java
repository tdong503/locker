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
    void should_return_corresponding_bag_when_take_bag_given_a_valid_ticket_provided() {
        InitManagedLockers();
        Integer bagId = 1;
        smartLockerRobot.saveBag(new Bag(bagId));

        Integer newBagId = 2;
        Ticket ticket = smartLockerRobot.saveBag(new Bag(newBagId));

        Bag actual = smartLockerRobot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(newBagId, actual.getId());
    }

    private void InitManagedLockers() {
        LinkedList<Locker> lockers = new LinkedList<>();
        Integer capacity = 2;
        lockers.add(new Locker(testLockerId1, capacity));
        lockers.add(new Locker(testLockerId2, capacity));
        this.smartLockerRobot = new SmartLockerRobot(lockers);
    }
}
