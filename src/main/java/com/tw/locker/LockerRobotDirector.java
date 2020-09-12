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
            String indent = "  ";
            StringBuilder lockerReport = new StringBuilder();
            ReportFieldsModel lockersReportFieldsModel = buildLockersReportDetail(indent, manager.getLockers());
            lockerReport.append(lockersReportFieldsModel.message);
            int freeCapacity = lockersReportFieldsModel.freeCapacity;
            int capacity = lockersReportFieldsModel.capacity;

            ReportFieldsModel robotsReportFieldsModel = buildRobotsReportDetail(indent, manager.getRobots());
            lockerReport.append(robotsReportFieldsModel.message);
            freeCapacity += robotsReportFieldsModel.freeCapacity;
            capacity += robotsReportFieldsModel.capacity;

            result.append(String.format("M %s %s\n", freeCapacity, capacity));
            result.append(lockerReport);
        }

        return result.toString();
    }

    // Locker report part
    private ReportFieldsModel buildLockersReportDetail(String indent, List<Locker> lockers) {
        ReportFieldsModel reportFieldsModel = new ReportFieldsModel();
        reportFieldsModel.message = buildLockerResponse(indent, lockers);
        reportFieldsModel.freeCapacity = getFreeCapacity(lockers);
        reportFieldsModel.capacity = getCapacity(lockers);

        return reportFieldsModel;
    }

    private StringBuilder buildLockerResponse(String indent, List<Locker> lockers) {
        StringBuilder lockerReport = new StringBuilder();

        for (Locker locker : lockers) {
            lockerReport.append(buildLockerResponse(indent, locker));
        }

        return lockerReport;
    }

    private String buildLockerResponse(String indent, Locker locker) {
        return String.format(indent + "L %s %s\n", locker.getFreeStorage(), locker.getCapacity());
    }

    private int getFreeCapacity(List<Locker> lockers) {
        return (int) lockers.stream().mapToDouble(Locker::getFreeStorage).sum();
    }

    private int getCapacity(List<Locker> lockers) {
        return (int) lockers.stream().mapToDouble(Locker::getCapacity).sum();
    }

    // Robots report part
    private ReportFieldsModel buildRobotsReportDetail(String indent, List<LockerRobotBase> robots) {
        ReportFieldsModel reportFieldsModel = new ReportFieldsModel();
        reportFieldsModel.message = buildRobotResponse(indent, robots);
        reportFieldsModel.freeCapacity = getRobotsFreeCapacity(robots);
        reportFieldsModel.capacity = getRobotsCapacity(robots);

        return reportFieldsModel;
    }

    private StringBuilder buildRobotResponse(String indent, List<LockerRobotBase> robots) {
        StringBuilder robotReport = new StringBuilder();

        for (LockerRobotBase robot : robots) {
            String secondLayerIndent = "    ";
            ReportFieldsModel reportFieldsModelInRobot = buildLockersReportDetail(secondLayerIndent, robot.lockers);

            robotReport.append(String.format(indent + "R %s %s\n", reportFieldsModelInRobot.freeCapacity, reportFieldsModelInRobot.capacity));
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
