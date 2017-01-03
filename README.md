## Course Tracker API Building Block

This [Blackboard](https://www.blackboard.com) building block provides an API to display assignments for the currently logged in user.

It pairs with [gokart/blackboard-course-tracker-b2-frontend](https://github.com/gokart/blackboard-course-tracker-b2-frontend).

### Example JSON Response

```json
{
  "user": {
    "firstName": "Jane",
    "lastName": "Doe",
    "username": "jdoe",
    "usernameHashed": "e6c15dd1428d85fc804d8b555e5746aae58f179c35e4cc21f379736439daa828"
  },
  "assignments": [
    {
      "name": "Week 1 Assignment",
      "displayName": null,
      "id": "_23_1",
      "status": "SUBMITTED",
      "points": 90,
      "maximumPoints": 100,
      "grade": "90.00",
      "dueDate": 1474453860000
    },
    {
      "name": "Week 1 Discussion",
      "displayName": null,
      "id": "_24_1",
      "status": "UNSUBMITTED",
      "points": null,
      "maximumPoints": 100,
      "grade": null,
      "dueDate": 1474454760000
    },
    {
      "name": "Week 2 Discussion",
      "displayName": null,
      "id": "_25_1",
      "status": "UNSUBMITTED",
      "points": null,
      "maximumPoints": 100,
      "grade": null,
      "dueDate": null
    },
  ],
  "courseDto": {
    "name": "Example Course",
    "startDate": 1473552000000,
    "endDate": 1475279999000
  }
}
```

### Installation

1. Copy the `.war` file to your Blackboard server, available under [Releases](https://github.com/gokart/blackboard-course-tracker-b2/releases).
2. The building block will appear in the Blackboard admin under Installed Tools, named `Course Tracker API` by vendor `GoKart Labs`.

### Usage

After logging in, query `assignments` using the course ID. Administrators can find a course's ID under _Customization...Properties_.

	http://example.com/webapps/gkl-coursetracker-BBLEARN/ws/currentuser/courses/<course_id>/assignments


## Development

See this project's [wiki](https://github.com/gokart/blackboard-course-tracker-b2/wiki) for complete details.
