package gkl.coursetracker.model;

import java.util.Calendar;

/**
 * Created by shane on 21/09/2016.
 */
public class AssignmentDto {

    private String name;
    private String displayName;
    private String id;
    private Status status;
    private Double points;
    private double maximumPoints;
    private String grade;
    private Calendar dueDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public double getMaximumPoints() {
        return maximumPoints;
    }

    public void setMaximumPoints(double maximumPoints) {
        this.maximumPoints = maximumPoints;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public enum Status {
        SUBMITTED, UNSUBMITTED
    }
}
