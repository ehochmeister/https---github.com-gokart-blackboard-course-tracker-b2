package gkl.coursetracker.ws;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseUtil;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.platform.gradebook2.GradableItem;
import blackboard.platform.gradebook2.GradeDetail;
import blackboard.platform.gradebook2.impl.GradableItemDAO;
import blackboard.platform.gradebook2.impl.GradeDetailDAO;
import gkl.coursetracker.model.AssignmentDto;
import gkl.coursetracker.model.AssignmentsResponse;
import gkl.coursetracker.model.CourseDto;
import gkl.coursetracker.model.UserDto;
import gkl.coursetracker.security.CurrentUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shane on 21/09/2016.
 */
@Path("/currentuser")
public class CurrentUserResource {

    private final GradableItemDAO gradableItemDao;
    private final GradeDetailDAO gradeDetailDao;
    private final CourseMembershipDbLoader courseMembershipDbLoader;
    private final CourseDbLoader courseDbLoader;

    public CurrentUserResource() throws PersistenceException {
        this.gradableItemDao = GradableItemDAO.get();
        this.gradeDetailDao = GradeDetailDAO.get();
        this.courseMembershipDbLoader = CourseMembershipDbLoader.Default.getInstance();
        this.courseDbLoader = CourseDbLoader.Default.getInstance();
    }

    @GET
    @Path("/courses/{courseId}/assignments")
    @Produces(MediaType.APPLICATION_JSON)
    public AssignmentsResponse getAssignments(@CurrentUser User user, @PathParam("courseId") String courseIdStr) {
        final Course course;
        try {
            course = courseDbLoader.loadByBatchUid(courseIdStr);
        } catch (PersistenceException e) {
            throw new WebApplicationException("Failed to load course", 500);
        }

        final CourseMembership courseMembership;
        try {
            courseMembership = courseMembershipDbLoader.loadByCourseAndUserId(course.getId(), user.getId());
        } catch (PersistenceException e) {
            throw new WebApplicationException("Failed to load course membership", 500);
        }

        if(!CourseUtil.courseIsAvailableByDuration(false, course, null, null, courseMembership)) {
            throw new WebApplicationException("Course is not available", 403);
        }

        final AssignmentsResponse assignmentsResponse = new AssignmentsResponse();
        assignmentsResponse.setUser(convertUserToDto(user));
        assignmentsResponse.setAssignments(getAssignmentDtos(course, courseMembership));
        assignmentsResponse.setCourse(convertCourseToDto(course));

        return assignmentsResponse;
    }

    private List<AssignmentDto> getAssignmentDtos(Course course, CourseMembership courseMembership) {
        final List<GradableItem> gradableItems = gradableItemDao.loadCourseGradebook(course.getId(), 0);
        List<AssignmentDto> assignmentDtos = new ArrayList<>(gradableItems.size());

        for (GradableItem gradableItem : gradableItems) {
            if(gradableItem.isVisibleToStudents()) {
                final GradeDetail gradeDetail = gradeDetailDao.getGradeDetail(gradableItem.getId(), courseMembership.getId());
                final AssignmentDto assignmentDto = convertGradableItemAndGradeDetailToDto(gradableItem, gradeDetail);
                assignmentDtos.add(assignmentDto);
            }
        }
        return assignmentDtos;
    }

    private static AssignmentDto convertGradableItemAndGradeDetailToDto(GradableItem gradableItem, GradeDetail gradeDetail) {
        final AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setId(gradableItem.getId().getExternalString());
        assignmentDto.setName(gradableItem.getTitle());
        assignmentDto.setDisplayName(gradableItem.getDisplayTitle());
        assignmentDto.setMaximumPoints(gradableItem.getPoints());
        assignmentDto.setDueDate(gradableItem.getDueDate());

        if(gradeDetail == null) {
            assignmentDto.setStatus(AssignmentDto.Status.UNSUBMITTED);
        } else {
            final String grade = gradeDetail.getGrade(gradableItem.getAggregationModel());
            if(grade != null) {
                assignmentDto.setStatus(AssignmentDto.Status.SUBMITTED);

                Double points = null;
                try {
                    points = Double.valueOf(grade);
                } catch (NumberFormatException ignored) {
                }
                if (points != null) {
                    assignmentDto.setPoints(points);
                    assignmentDto.setGrade(gradableItem.getSchemaValue(points));
                }
            } else {
                assignmentDto.setStatus(AssignmentDto.Status.UNSUBMITTED);
            }
        }

        return assignmentDto;
    }

    private static UserDto convertUserToDto(User user) {
        final UserDto userDto = new UserDto();
        userDto.setFirstName(user.getGivenName());
        userDto.setLastName(user.getFamilyName());
        userDto.setUsername(user.getUserName());
        userDto.setUsernameHashed(user.getUserName());
        return userDto;
    }

    private static CourseDto convertCourseToDto(Course course) {
        final CourseDto courseDto = new CourseDto();
        courseDto.setName(course.getTitle());
        courseDto.setStartDate(course.getStartDate());
        courseDto.setEndDate(course.getEndDate());
        return courseDto;
    }

}
