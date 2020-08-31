package com.tw.locker;

import java.util.List;
import java.util.Optional;

public class PrimaryLockerRobot {
    private List<Locker> lockers;

    public PrimaryLockerRobot(List<Locker> lockers) {
        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        Optional<Locker> availableLocker = lockers.stream().filter(Locker::hasStorage).findFirst();
        if(availableLocker.isPresent()) {
            return availableLocker.get().saveBag(bag);
        }

        return null;
    }
}
