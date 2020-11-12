package domain;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class CourseBuilder {

    private List<Teacher> teachers;
    private Set<Student> students = new HashSet<>();
    private Set<Set<Student>> allPossibleStudentsSets;
    private Schedule schedule;

    public CourseBuilder(Schedule aSchedule, List<Teacher> availableTeachers) {
        this.teachers = availableTeachers.stream().filter(teacher -> teacher.isAvailableFor(aSchedule)).collect(Collectors.toList());
        this.schedule = aSchedule;
    }

    public CourseBuilder addPossibleStudents(List<Student> availableStudents) {
        this.students = availableStudents.stream()
                .filter(student -> student.isAvailableFor(this.schedule))
                .collect(Collectors.toSet());
        return this;
    }

    public CourseBuilder allPossibleStudentsCombinations() {
        this.allPossibleStudentsSets = Sets.powerSet(students);
        return this;
    }

    public List<Course> buildValidCourses() {
        List<Course> validCourses =
                this.allPossibleStudentsSets.stream()
                .filter(this::canBeTogether)
                .map(students -> teachers.stream().map(availableTeacher -> buildCourse(students, availableTeacher))
                .collect(Collectors.toList())).collect(Collectors.toList())
                .stream().flatMap(Collection::stream).collect(Collectors.toList());
        return validCourses;
    }

    private Course buildCourse(Set<Student> students, Teacher availableTeacher) {
        Level level = students.stream().findFirst().get().getLevel();
        Modality modality = students.stream().findFirst().get().getModality();
        Course course = new Course(availableTeacher, this.schedule, level, modality);
        course.enrollStudents(students);
        return course;
    }

    private boolean canBeTogether(Set<Student> someStudents) {
        Student student = someStudents.stream().findFirst().orElse(null);
        return !Objects.isNull(student) && student.getModality().acceptsStudentQuantity(students.size()) && someStudents.stream().allMatch(peer -> peer.acceptsModality(student.getModality()) && peer.acceptsLevel(student.getLevel()));
    }
}
