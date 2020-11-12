package domain;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CourseGenerator {

    private List<Teacher> availableTeachers;
    private List<Student> availableStudents;

    public CourseGenerator(List<Teacher> availableTeachers, List<Student> availableStudents) {
        this.availableTeachers = availableTeachers;
        this.availableStudents = availableStudents;
    }

    public List<Course> generateAll() {

        List<Schedule> allPossibleSchedules = this.allPossibleSchedules();
        List<CourseBuilder> possibleCourses = allPossibleSchedules.stream().map(
                aSchedule -> new CourseBuilder(aSchedule, availableTeachers)
        ).collect(Collectors.toList());

        List<CourseBuilder> possibleCoursesWithStudents = possibleCourses.stream().map(possibleCourse -> possibleCourse.addPossibleStudents(availableStudents)).collect(Collectors.toList());
        List<CourseBuilder> allPossibleStudentCombination = possibleCoursesWithStudents.stream().map(CourseBuilder::allPossibleStudentsCombinations).collect(Collectors.toList());
        List<Course> courses = allPossibleStudentCombination.stream().map(CourseBuilder::buildValidCourses).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());

        return courses;
    }

    private List<Schedule> allPossibleSchedules() {
        DayOfWeek[] dayOfWeeks = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        return new ArrayList<DayOfWeek>(Arrays.asList(dayOfWeeks))
                .stream()
                .flatMap(dayOfWeek -> IntStream.range(9, 19).boxed().map(time -> new Schedule(dayOfWeek, time))).collect(Collectors.toList());
    }
}
