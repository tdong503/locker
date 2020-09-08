package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LockerRobotManager {

    private final List<Locker> lockers;
    private final List<LockerRobotBase> robots;

    public LockerRobotManager(LinkedList<Locker> lockers, LinkedList<LockerRobotBase> robots) {
        this.lockers = lockers;
        this.robots = robots;
    }

    public Ticket saveBag(Bag bag) {
        if(this.robots != null){
            Optional<LockerRobotBase> availableRobots = this.robots.stream().filter(r -> r.getAvailableLocker().isPresent()).findFirst();
            if(availableRobots.isPresent()){
                return availableRobots.get().saveBag(bag);
            }
        }

        if(this.lockers != null){
            Optional<Locker> availableLocker = this.lockers.stream().filter(Locker::hasStorage).findFirst();
            if(availableLocker.isPresent()) {
                return availableLocker.get().saveBag(bag);
            }
        }

        throw new NoStorageException();
    }

    public Bag takeBag(Ticket ticket) {
        Optional<Locker> correspondingLocker = lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();

        return correspondingLocker.get().takeBag(ticket);
    }
}
