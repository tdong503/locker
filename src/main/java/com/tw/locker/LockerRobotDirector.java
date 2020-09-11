package com.tw.locker;

import java.util.List;

public class LockerRobotDirector {
    private List<LockerRobotManager> lockerRobotManagers;

    public LockerRobotDirector(List<LockerRobotManager> lockerRobotManagers) {
        this.lockerRobotManagers = lockerRobotManagers;
    }

    public String generateReport() {
        StringBuilder result = new StringBuilder();

        for (LockerRobotManager manager : lockerRobotManagers) {
            StringBuilder lockerReport = new StringBuilder();
            int freeCapacity = 0;
            int capacity = 0;
            ReportFieldsModel reportFieldsModel = buildLockersReportDetail(1, manager.getLockers());
            lockerReport.append(reportFieldsModel.message);
            freeCapacity = getFreeCapacity(manager.getLockers());
            capacity = getCapacity(manager.getLockers());

            List<LockerRobotBase> robots = manager.getRobots();
            lockerReport.append(buildRobotResponse(robots));
            freeCapacity += getRobotsFreeCapacity(robots);
            capacity += getRobotsCapacity(robots);

            result.append(String.format("M %s %s\n", freeCapacity, capacity));
            result.append(lockerReport);
        }

        return result.toString();
    }

    private ReportFieldsModel buildLockersReportDetail(int layer, List<Locker> lockers) {
        ReportFieldsModel reportFieldsModel = new ReportFieldsModel();
        reportFieldsModel.message = buildLockerResponse(layer, lockers);
        reportFieldsModel.freeCapacity = getFreeCapacity(lockers);
        reportFieldsModel.capacity = getCapacity(lockers);

        return reportFieldsModel;
    }

    private StringBuilder buildLockerResponse(int layer, List<Locker> lockers) {
        StringBuilder lockerReport = new StringBuilder();

        for (Locker locker : lockers) {
            lockerReport.append(buildLockerResponse(layer, locker));
        }

        return lockerReport;
    }

    private String buildLockerResponse(int layer, Locker locker) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < layer; i++) {
            indent.append("  ");
        }
        return String.format(indent + "L %s %s\n", locker.getFreeStorage(), locker.getCapacity());
    }

    private int getFreeCapacity(List<Locker> lockers) {
        return (int) lockers.stream().mapToDouble(Locker::getFreeStorage).sum();
    }

    private int getCapacity(List<Locker> lockers) {
        return (int) lockers.stream().mapToDouble(Locker::getCapacity).sum();
    }

    private StringBuilder buildRobotResponse(List<LockerRobotBase> robots) {
        StringBuilder robotReport = new StringBuilder();

        for (LockerRobotBase robot : robots) {
            ReportFieldsModel reportFieldsModelInRobot = buildLockersReportDetail(2, robot.lockers);

            robotReport.append(String.format("  R %s %s\n", reportFieldsModelInRobot.freeCapacity, reportFieldsModelInRobot.capacity));
            if (robot.lockers.size() > 1) {
                robotReport.append(reportFieldsModelInRobot.message);
            }
        }

        return robotReport;
    }

    private int getRobotsFreeCapacity(List<LockerRobotBase> robots) {
        return (int) robots.stream().flatMap(robot -> robot.lockers.stream()).mapToDouble(Locker::getFreeStorage).sum();
    }

    private int getRobotsCapacity(List<LockerRobotBase> robots) {
        return (int) robots.stream().flatMap(robot -> robot.lockers.stream()).mapToDouble(Locker::getCapacity).sum();
    }

    static class ReportFieldsModel {
        StringBuilder message;
        int freeCapacity;
        int capacity;
    }
}
