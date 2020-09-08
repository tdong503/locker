package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;

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
        if(availableLocker.isPresent()) {
            return availableLocker.get().saveBag(bag);
        }

        throw new NoStorageException();
    }
}
