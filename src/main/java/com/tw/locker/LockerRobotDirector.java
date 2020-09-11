package com.tw.locker;

import java.awt.*;
import java.util.List;

public class LockerRobotDirector {
    private List<LockerRobotManager> lockerRobotManagers;

    public LockerRobotDirector(List<LockerRobotManager> lockerRobotManagers) {
        this.lockerRobotManagers = lockerRobotManagers;
    }

    public String generateReport() {
        StringBuilder result = new StringBuilder();

        for (LockerRobotManager manager : lockerRobotManagers) {
            StringBuilder lockerReport = new StringBuilder();
            int freeCapacity = 0;
            int capacity = 0;
            List<Locker> lockers = manager.getLockers();
            for (Locker locker : lockers) {
                lockerReport.append(String.format("  L %s %s\n", locker.getFreeStorage(), locker.getCapacity()));
                freeCapacity += locker.getFreeStorage();
                capacity += locker.getCapacity();
            }

            int lockerInRobotFreeCapacity = 0;
            int lockerInRobotCapacity = 0;
            StringBuilder robotReport = new StringBuilder();
            List<LockerRobotBase> robots = manager.getRobots();
            for (LockerRobotBase robot : robots) {
                List<Locker> lockers1 = robot.lockers;
                for (Locker locker : lockers1) {
                    robotReport.append(String.format("    L %s %s\n", locker.getFreeStorage(), locker.getCapacity()));
                    lockerInRobotFreeCapacity += locker.getFreeStorage();
                    lockerInRobotCapacity += locker.getCapacity();
                }
                freeCapacity += lockerInRobotFreeCapacity;
                capacity += lockerInRobotCapacity;
                lockerReport.append(String.format("  R %s %s\n", lockerInRobotFreeCapacity, lockerInRobotCapacity));
                if(lockers1.size() > 1) {
                    lockerReport.append(robotReport);
                }
            }

            result.append(String.format("M %s %s\n", freeCapacity, capacity));
            result.append(lockerReport);
        }

        return result.toString();
    }
}
