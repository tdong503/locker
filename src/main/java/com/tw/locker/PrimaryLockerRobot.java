package com.tw.locker;

import java.util.Optional;

public class PrimaryLockerRobot extends LockerRobotBase {
    @Override
    protected Optional<Locker> getAvailableLocker() {
        return lockers.stream().filter(Locker::hasStorage).findFirst();
    }
}
