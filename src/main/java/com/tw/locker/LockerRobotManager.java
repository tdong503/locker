package com.tw.locker;

import java.util.List;
import java.util.Optional;

public class LockerRobotManager extends LockerRobotBase {

    private List<LockerRobotBase> robots;

    public void setRobots(List<LockerRobotBase> robots) {
        this.robots = robots;
    }

    @Override
    protected Optional<Locker> getAvailableLockerByTicket(Ticket ticket) {
        if(this.lockers!=null){
            Optional<Locker> correspondingLocker = super.getAvailableLockerByTicket(ticket);
            if(correspondingLocker.isPresent()){
                return correspondingLocker;
            }
        }

        if(this.robots != null){
            Optional<LockerRobotBase> correspondingRobot = this.robots.stream().filter(r -> r.lockers.stream().anyMatch(l -> l.getLockerId().equals(ticket.getLockerId()))).findFirst();
            if(correspondingRobot.isPresent()) {
                return correspondingRobot.get().getAvailableLockerByTicket(ticket);
            }
        }

        return Optional.empty();
    }

    @Override
    protected Optional<Locker> getAvailableLocker() {
        if(this.robots != null){
            Optional<Locker> availableLocker =  this.robots.stream().filter(r -> r.getAvailableLocker().isPresent()).map(r -> r.getAvailableLocker().get()).findFirst();
            if(availableLocker.isPresent()) {
                return availableLocker;
            }
        }

        if(this.lockers != null){
            return this.lockers.stream().filter(Locker::hasStorage).findFirst();
        }

        return Optional.empty();
    }
}
