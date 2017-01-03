package gkl.coursetracker.model;

import java.util.List;

/**
 * Created by shane on 21/09/2016.
 */
public class AssignmentsResponse {

    private UserDto user;
    private List<AssignmentDto> assignments;
    private CourseDto course;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<AssignmentDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDto> assignments) {
        this.assignments = assignments;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

}
