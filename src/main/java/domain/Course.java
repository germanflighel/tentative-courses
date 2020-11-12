package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Course {

    private Teacher teacher;
    private Set<Enrollment> enrollments = new HashSet<>();
    private Schedule schedule;
    private Level level;
    private Modality modality;

    public Course(Teacher teacher, Schedule schedule, Level level, Modality modality) {
        this.teacher = teacher;
        this.schedule = schedule;
        this.level = level;
        this.modality = modality;
    }

    public void enrollStudent(Student student) {
        Enrollment enrollment = new Enrollment(student, student.needsEnrollmentConfirmationFor(this));
        enrollments.add(enrollment);
    }

    public Boolean acceptsNewStudent() {
        return modality.acceptsMoreStudents(enrollments.size());
    }

    public boolean hasLevel(Level level) {
        return level == this.level;
    }

    public boolean hasModality(Modality modality) {
        return modality == this.modality;
    }

    public void fillCourse(Set<Student> students) {
        for (Student aStudent : students) {
            if (aStudent.canEnrollFor(this)) {
                this.enrollStudent(aStudent);
            }
        }
    }

    public boolean isTheSameAs(Course anotherCourse) {
        return
                anotherCourse.hasModality(this.modality) &&
                anotherCourse.hasLevel(this.level) &&
                anotherCourse.hasSchedule(this.schedule) &&
                anotherCourse.hasTeacher(this.teacher) &&
                anotherCourse.hasEnrolledStudents(this.enrollments);
    }

    private Boolean hasEnrolledStudents(Set<Enrollment> enrollments) {
        return enrolledStudents(enrollments).equals(enrolledStudents(this.enrollments));
    }

    private Set<Student> enrolledStudents(Set<Enrollment> enrollments) {
        return enrollments.stream().map(Enrollment::getStudent).collect(Collectors.toSet());
    }

    private Set<Student> enrolledStudents() {
        return enrollments.stream().map(Enrollment::getStudent).collect(Collectors.toSet());
    }

    private Boolean hasTeacher(Teacher teacher) {
        return this.teacher.equals(teacher);
    }

    private Boolean hasSchedule(Schedule schedule) {
        return this.schedule.accepts(schedule);
    }

    public Boolean hasAStudent(Student student) {
        return this.enrolledStudents().contains(student);
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
