package gkl.coursetracker.model;

import java.util.Calendar;

/**
 * Created by shane on 22/09/2016.
 */
public class CourseDto {

    private String name;
    private Calendar startDate;
    private Calendar endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
}
