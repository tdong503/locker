package com.tw.locker;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartLockerRobot extends LockerRobotBase {

    public SmartLockerRobot(List<Locker> lockers) {
        super(lockers);
    }

    @Override
    protected Optional<Locker> getAvailableLocker() {
        return lockers.stream().filter(Locker::hasStorage).max(Comparator.comparingInt(Locker::getFreeStorage));
    }
}
