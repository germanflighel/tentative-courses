package domain;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class CourseBuilder {

    private List<Teacher> teachers;
    private Set<Student> students = new HashSet<>();
    private Set<Set<Student>> allPossibleStudentsSets;
    private Schedule schedule;
    private Level level;
    private Modality modality;

    public CourseBuilder(Schedule aSchedule, List<Teacher> availableTeachers) {
        this.teachers = availableTeachers.stream().filter(teacher -> teacher.isAvailableFor(aSchedule)).collect(Collectors.toList());
        this.schedule = aSchedule;
    }

    public CourseBuilder(List<Teacher> teachers, Schedule schedule, Set<Student> students) {
        this.teachers = teachers;
        this.schedule = schedule;
        this.students = students;
    }

    public CourseBuilder addPossibleStudents(List<Student> availableStudents) {
        Set<Student> students = availableStudents.stream()
                .filter(student -> student.isAvailableFor(this.schedule))
                .collect(Collectors.toSet());

        return new CourseBuilder(this.teachers, this.schedule, students);
    }

    public CourseBuilder allPossibleStudentsCombinationsv2() {
        this.allPossibleStudentsSets = Sets.powerSet(students);
        return this;
    }

    public List<Course> allPossibleStudentsCombinations() {
        List<Course> validCourses = this.students.stream().map(student ->
                teachers.stream().map(availableTeacher -> {
                    Course course = new Course(availableTeacher, this.schedule, student.getLevel(), student.getModality());
                    course.enrollStudent(student);
                    course.fillCourse(students);
                    return course;
                }).collect(Collectors.toList())
        ).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());
        return validCourses;
    }

    public List<Course> buildPossibles() {
        List<Course> validCourses = this.allPossibleStudentsSets.stream()
                .filter(this::canBeTogether)
                .map(students -> teachers.stream().map(availableTeacher -> {
                    Level level = students.stream().findFirst().get().getLevel();
                    Modality modality = students.stream().findFirst().get().getModality();
                    Course course = new Course(availableTeacher, this.schedule, level, modality);
                    course.enrollStudents(students);
                    return course;
                }).collect(Collectors.toList())
        ).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toList());
        return validCourses;
    }

    private boolean canBeTogether(Set<Student> someStudents) {
        Student student = someStudents.stream().findFirst().orElse(null);
        return !Objects.isNull(student) && student.getModality().acceptsStudentQuantity(students.size()) && someStudents.stream().allMatch(peer -> peer.acceptsModality(student.getModality()) && peer.acceptsLevel(student.getLevel()));
    }
}
