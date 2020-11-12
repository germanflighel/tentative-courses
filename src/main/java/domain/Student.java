package domain;

import java.util.List;

public class Student {
    private Modality modality;
    private Level level;
    private List<Schedule> availableTimes;

    public List<Schedule> getAvailableTimes() {
        return availableTimes;
    }

    public Student(Modality modality, Level level, List<Schedule> availableTimes) {
        this.modality = modality;
        this.level = level;
        this.availableTimes = availableTimes;
    }

    public Level getLevel() {
        return level;
    }

    public Modality getModality() {
        return modality;
    }

    public Boolean needsEnrollmentConfirmationFor(Course course) {
        return availableTimes.stream().anyMatch(availableTime -> availableTime.accepts(course.getSchedule()));
    }

    public Boolean canEnrollFor(Course course) {
        return course.acceptsNewStudent() && course.hasLevel(this.level) && course.hasModality(this.modality) && !course.hasAStudent(this);
    }

}
