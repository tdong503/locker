package com.tw.locker;

import java.util.List;
import java.util.Optional;

public class PrimaryLockerRobot extends RobotBase {

    public PrimaryLockerRobot(List<Locker> lockers) {
        super(lockers);
    }

    @Override
    protected Optional<Locker> getAvailableLocker() {
        return lockers.stream().filter(Locker::hasStorage).findFirst();
    }
}
