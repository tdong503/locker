package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import com.tw.locker.exceptions.UnrecognizedTicketException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

public abstract class LockerRobotBase {
    protected List<Locker> lockers;

    public void setLockers(List<Locker> lockers) {
        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        Optional<Locker> availableLocker = getAvailableLocker();
        if(availableLocker.isPresent()) {
            return availableLocker.get().saveBag(bag);
        }

        throw new NoStorageException();
    }

    public Bag takeBag(Ticket ticket) {
        if (ticket == null) {
            throw new UnrecognizedTicketException();
        }

        Optional<Locker> availableLocker = lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();
        if(availableLocker.isPresent()) {
            return availableLocker.get().takeBag(ticket);
        }

        throw new FakeTicketException();
    }

    protected abstract Optional<Locker> getAvailableLocker();
}
