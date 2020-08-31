package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;

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

        throw new NoStorageException();
    }

    public Bag takeBag(Ticket ticket) {
        Optional<Locker> availableLocker = lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();
        if(availableLocker.isPresent()) {
            return availableLocker.get().takeBag(ticket);
        }

        return null;
    }
}
