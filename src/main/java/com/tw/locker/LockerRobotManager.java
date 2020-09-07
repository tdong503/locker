package com.tw.locker;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LockerRobotManager {

    protected final List<Locker> lockers;

    public LockerRobotManager(LinkedList<Locker> lockers) {
        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        Optional<Locker> availableLocker = lockers.stream().filter(Locker::hasStorage).findFirst();
        return availableLocker.get().saveBag(bag);
    }
}
