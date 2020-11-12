package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseGenerator {

    private List<Teacher> availableTeachers;
    private List<Student> availableStudents;

    public CourseGenerator(List<Teacher> availableTeachers, List<Student> availableStudents) {
        this.availableTeachers = availableTeachers;
        this.availableStudents = availableStudents;
    }

    public List<Course> generateAll() {
        List<Course> courses = new ArrayList<>();

        availableStudents.forEach(student -> {
            student.getAvailableTimes().forEach(availableTime -> {
                List<Teacher> availableTeachers = this.availableTeachers.stream().filter(teacher -> teacher.isAvailableFor(availableTime)).collect(Collectors.toList());
                availableTeachers.forEach(availableTeacher -> {
                    Course course = new Course(availableTeacher, availableTime, student.getLevel(), student.getModality());
                    course.enrollStudent(student);
                    course.fillCourse(availableStudents.stream().filter(aStudent -> aStudent != student).collect(Collectors.toSet()));
                    if (courses.stream().noneMatch(anotherCourse -> anotherCourse.isTheSameAs(course)))
                        courses.add(course);
                });
            });
        });

        return courses;
    }
}
