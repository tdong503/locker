package com.tw.locker;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public abstract class LockerRobotBase {
    protected List<Locker> lockers;

    public void setLockers(List<Locker> lockers) {
        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        return BagOperation.saveBag(bag, this::getAvailableLocker);
    }

    protected abstract Optional<Locker> getAvailableLocker();

    public Bag takeBag(Ticket ticket) {
        return BagOperation.takeBag(ticket, (t) -> getAvailableLockerByTicket(ticket));
    }

    protected Optional<Locker> getAvailableLockerByTicket(Ticket ticket) {
        return lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();
    }
}
