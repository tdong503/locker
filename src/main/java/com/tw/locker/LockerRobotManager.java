package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LockerRobotManager {

    private List<Locker> lockers;
    private List<LockerRobotBase> robots;

    public void setLockers(LinkedList<Locker> lockers) {
        this.lockers = lockers;
    }

    public void setRobots(LinkedList<LockerRobotBase> robots) {
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
        if(this.lockers!=null){
            Optional<Locker> correspondingLocker = this.lockers.stream().filter(x -> x.getLockerId().equals(ticket.getLockerId())).findFirst();
            if(correspondingLocker.isPresent()){
                return correspondingLocker.get().takeBag(ticket);
            }
        }

        if(this.robots != null){
            Optional<LockerRobotBase> correspondingRobot = Optional.empty();

            for (LockerRobotBase robot: this.robots) {
                Optional<Locker> matchedLocker = robot.lockers.stream().filter(l -> l.getLockerId().equals(ticket.getLockerId())).findFirst();
                if(matchedLocker.isPresent()){
                    correspondingRobot = Optional.of(robot);
                }
            }

            if(correspondingRobot.isPresent()){
                return correspondingRobot.get().takeBag(ticket);
            }
        }

        throw new FakeTicketException();
    }
}
