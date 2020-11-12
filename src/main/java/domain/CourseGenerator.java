package domain;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CourseGenerator {

    private List<Teacher> availableTeachers;
    private List<Student> availableStudents;

    public CourseGenerator(List<Teacher> availableTeachers, List<Student> availableStudents) {
        this.availableTeachers = availableTeachers;
        this.availableStudents = availableStudents;
    }

    public List<Course> generateAll() {

        List<Course> courses = new ArrayList<>();

/*

        List<Schedule> allPossibleSchedules = this.allPossibleSchedules();


        List<CourseBuilder> possibleCourses = allPossibleSchedules.stream().map(
                aSchedule -> new CourseBuilder(aSchedule, availableTeachers)
        ).collect(Collectors.toList());

        possibleCourses = possibleCourses.stream().map(possibleCourse -> possibleCourse.addPossibleStudents(availableStudents)).collect(Collectors.toList());
        return possibleCourses.stream().map(CourseBuilder::allPossibleStudentsCombinations).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<CourseBuilder> courseBuilders = possibleCourses.stream().map(CourseBuilder::allPossibleStudentsCombinationsv2).collect(Collectors.toList()).stream().collect(Collectors.toList());
        courses = courseBuilders.stream().map(CourseBuilder::buildPossibles).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());


        return courses;
*/
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

    private List<Schedule> allPossibleSchedules() {
        DayOfWeek[] dayOfWeeks = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        return new ArrayList<DayOfWeek>(Arrays.asList(dayOfWeeks))
                .stream()
                .flatMap(dayOfWeek -> IntStream.range(9, 19).boxed().map(time -> new Schedule(dayOfWeek, time))).collect(Collectors.toList());
    }
}
