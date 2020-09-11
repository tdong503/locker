package com.tw.locker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LockerRobotDirectorTests {
    private LockerRobotDirector lockerRobotDirector;
    private LockerRobotManager lockerRobotManager;
    private FileOperator fileOperator = new FileOperator();

    @BeforeEach
    void InitLockerRobotManager() {
        lockerRobotManager = new LockerRobotManager();
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_only_with_two_lockers() throws IOException {
        List<FreeCapacityAndInitCapacityModel> freeCapacityAndInitCapacityListForLockers = new ArrayList<>();
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitCapacityModel(0, 8));
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitCapacityModel(3, 5));
        buildLockers(freeCapacityAndInitCapacityListForLockers);

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("result.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    private void buildLockerRobotDirector() {
        this.lockerRobotDirector = new LockerRobotDirector(Collections.singletonList(this.lockerRobotManager));
    }

    private LinkedList<Locker> buildLockers(List<FreeCapacityAndInitCapacityModel> freeCapacityAndInitCapacityList) {
        LinkedList<Locker> lockers = new LinkedList<>();

        for (FreeCapacityAndInitCapacityModel freeCapacityAndInitCapacity: freeCapacityAndInitCapacityList) {
            lockers.add(buildLocker(freeCapacityAndInitCapacity.freeCapacity, freeCapacityAndInitCapacity.initCapacity));
        }

        this.lockerRobotManager.setLockers(lockers);

        return lockers;
    }

    private Locker buildLocker(int freeCapacity, int capacity) {
        String lockerId = UUID.randomUUID().toString();
        Locker locker = new Locker(lockerId, capacity);

        int saveBagCount = capacity - freeCapacity;
        for (int i = 0; i < saveBagCount; i++) {
            locker.saveBag(new Bag());
        }

        return locker;
    }

    static class FreeCapacityAndInitCapacityModel
    {
        int freeCapacity;
        int initCapacity;

        public FreeCapacityAndInitCapacityModel(int freeCapacity, int initCapacity) {
            this.freeCapacity = freeCapacity;
            this.initCapacity = initCapacity;
        }
    }
}

