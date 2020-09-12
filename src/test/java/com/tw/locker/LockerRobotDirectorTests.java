package com.tw.locker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LockerRobotDirectorTests {
    private LockerRobotDirector lockerRobotDirector;
    private LockerRobotManager lockerRobotManager;
    private final FileOperator fileOperator = new FileOperator();

    @BeforeEach
    void InitLockerRobotManager() {
        lockerRobotManager = new LockerRobotManager();
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_only_with_two_lockers() throws IOException {
        List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityListForLockers = new ArrayList<>();
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(0, 8));
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(3, 5));
        buildLockers(freeCapacityAndInitCapacityListForLockers);

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("a_locker_robot_manager_only_with_two_lockers.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_with_one_robot_and_one_locker() throws IOException {
        buildLockers(Collections.singletonList(new FreeCapacityAndInitialCapacityModel(2, 5)));
        buildRobot(Collections.singletonList(new FreeCapacityAndInitialCapacityModel(1, 5)));

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("a_locker_robot_manager_with_one_robot_and_one_locker.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_with_one_locker_and_one_robot_has_two_lockers() throws IOException {
        buildLockers(Collections.singletonList(new FreeCapacityAndInitialCapacityModel(2, 5)));
        List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityListForLockers = new ArrayList<>();
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(1, 5));
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(2, 8));
        buildRobot(freeCapacityAndInitCapacityListForLockers);

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("a_locker_robot_manager_with_one_locker_and_one_robot_has_two_lockers.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_with_two_robots() throws IOException {
        List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityListForLockers = new ArrayList<>();
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(3, 9));
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(2, 4));
        buildRobots(freeCapacityAndInitCapacityListForLockers);

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("a_locker_robot_manager_with_two_robots.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void should_return_expected_result_when_generate_report_given_a_locker_robot_manager_only_with_two_lockers_and_a_locker_and_a_robot_without_manager() throws IOException {
        List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityListForLockers = new ArrayList<>();
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(0, 8));
        freeCapacityAndInitCapacityListForLockers.add(new FreeCapacityAndInitialCapacityModel(3, 5));
        buildLockers(freeCapacityAndInitCapacityListForLockers);

        buildLocker(2, 6);
        LockerRobotBase robot = new PrimaryLockerRobot();
        robot.setLockers(Collections.singletonList(buildLocker(3, 8)));

        buildLockerRobotDirector();
        String expected = fileOperator.fileReadToText("a_locker_robot_manager_only_with_two_lockers_and_a_robot_without_manager.txt");

        String actual = lockerRobotDirector.generateReport();

        assertEquals(expected, actual);
    }

    private void buildLockerRobotDirector() {
        this.lockerRobotDirector = new LockerRobotDirector(Collections.singletonList(this.lockerRobotManager));
    }

    private void buildLockers(List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityList) {
        LinkedList<Locker> lockers = new LinkedList<>();

        for (FreeCapacityAndInitialCapacityModel freeCapacityAndInitCapacity : freeCapacityAndInitCapacityList) {
            lockers.add(buildLocker(freeCapacityAndInitCapacity.freeCapacity, freeCapacityAndInitCapacity.initialCapacity));
        }

        this.lockerRobotManager.setLockers(lockers);
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

    private void buildRobot(List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityList) {
        LinkedList<Locker> lockers = new LinkedList<>();

        for (FreeCapacityAndInitialCapacityModel freeCapacityAndInitCapacity : freeCapacityAndInitCapacityList) {
            lockers.add(buildLocker(freeCapacityAndInitCapacity.freeCapacity, freeCapacityAndInitCapacity.initialCapacity));
        }

        LockerRobotBase robot = new PrimaryLockerRobot();
        robot.setLockers(lockers);

        this.lockerRobotManager.setRobots(Collections.singletonList(robot));
    }

    private void buildRobots(List<FreeCapacityAndInitialCapacityModel> freeCapacityAndInitCapacityList) {
        LinkedList<LockerRobotBase> robots = new LinkedList<>();

        for (FreeCapacityAndInitialCapacityModel freeCapacityAndInitCapacity : freeCapacityAndInitCapacityList) {
            Locker locker = buildLocker(freeCapacityAndInitCapacity.freeCapacity, freeCapacityAndInitCapacity.initialCapacity);
            LockerRobotBase robot = new PrimaryLockerRobot();
            robot.setLockers(Collections.singletonList(locker));
            robots.add(robot);
        }

        this.lockerRobotManager.setRobots(robots);
    }

    static class FreeCapacityAndInitialCapacityModel {
        int freeCapacity;
        int initialCapacity;

        public FreeCapacityAndInitialCapacityModel(int freeCapacity, int initialCapacity) {
            this.freeCapacity = freeCapacity;
            this.initialCapacity = initialCapacity;
        }
    }
}

