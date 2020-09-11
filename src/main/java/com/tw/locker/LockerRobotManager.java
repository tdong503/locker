package com.tw.locker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class LockerRobotManager {

    private List<Locker> lockers = new ArrayList<>();
    private List<LockerRobotBase> robots = new ArrayList<>();

    public Ticket saveBag(Bag bag) {
        return BagOperation.saveBag(bag, this::getAvailableLocker);
    }

    public Bag takeBag(Ticket ticket) {
        return BagOperation.takeBag(ticket, (t) -> getCorrespondingLockerByTicket(ticket));
    }

    private Optional<Locker> getCorrespondingLockerByTicket(Ticket ticket) {
        Optional<Locker> correspondingLocker = this.lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();
        if (correspondingLocker.isPresent()) {
            return correspondingLocker;
        }

        Optional<LockerRobotBase> correspondingRobot = this.robots.stream().filter(r -> r.lockers.stream().anyMatch(l -> l.getLockerId().equals(ticket.getLockerId()))).findFirst();
        if (correspondingRobot.isPresent()) {
            return correspondingRobot.get().getAvailableLockerByTicket(ticket);
        }

        return Optional.empty();
    }

    private Optional<Locker> getAvailableLocker() {
        Optional<Locker> availableLocker = this.robots.stream().filter(r -> r.getAvailableLocker().isPresent()).map(r -> r.getAvailableLocker().get()).findFirst();
        if (availableLocker.isPresent()) {
            return availableLocker;
        }

        return this.lockers.stream().filter(Locker::hasStorage).findFirst();
    }
}
