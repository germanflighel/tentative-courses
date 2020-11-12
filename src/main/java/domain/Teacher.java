package domain;

import java.util.List;

public class Teacher {
    private List<Schedule> availableTimes;

    public List<Schedule> getAvailableTimes() {
        return availableTimes;
    }

    public Teacher(List<Schedule> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public Boolean isAvailableFor(Schedule aTime) {
        return availableTimes.stream().anyMatch(anAvailableTime -> anAvailableTime.accepts(aTime));
    }
}
