package domain;

import domain.Modality;

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

    public Boolean acceptsATime(Schedule teachingTimes) {
        return availableTimes.stream().anyMatch(availableTime -> true);
    }

    public Boolean acceptsModality(Modality modality) {
        return this.modality == modality;
    }

    public Boolean acceptsLevel(Level level) {
        return this.level == level;
    }

    public Level getLevel() {
        return level;
    }

    public Modality getModality() {
        return modality;
    }

    public Boolean needsEnrollmentConfirmationFor(Course course) {
        // TODO: Add confirmation checking via course.schedule matching
        return false;
    }

    public Boolean canEnrollFor(Course course) {
        return course.acceptsNewStudent() && course.hasLevel(this.level) && course.hasModality(this.modality) && !course.hasAStudent(this);
    }

    public Boolean isAvailableFor(Schedule schedule) {
        return availableTimes.stream().anyMatch(anAvailableTime -> anAvailableTime.accepts(schedule));
    }
}
