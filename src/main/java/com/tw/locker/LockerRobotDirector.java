package com.tw.locker;

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
            for (Locker locker :
                    lockers) {
                lockerReport.append(String.format("  L %s %s\n", locker.getFreeStorage(), locker.getCapacity()));
                freeCapacity += locker.getFreeStorage();
                capacity += locker.getCapacity();
            }
            result.append(String.format("M %s %s\n", freeCapacity, capacity));
            result.append(lockerReport);
        }

        return result.toString();
    }
}
