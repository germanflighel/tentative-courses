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

    public void enrollStudents(Set<Student> students) {
        // The confirmation value -isConfirmed- could be delegated to tag those which need. With no tolerance, it is assumed true
        this.enrollments.addAll(students.stream().map(student -> new Enrollment(student, true)).collect(Collectors.toSet()));
    }
}
