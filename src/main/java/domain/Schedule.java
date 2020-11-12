package domain;

import java.time.DayOfWeek;
import java.util.List;

public class Schedule {

    private DayOfWeek day;
    private Integer time;

    public Schedule(DayOfWeek day, Integer time) {
        this.day = day;
        this.time = time;
    }

    public boolean accepts(Schedule aTime) {
        return aTime.day == day && aTime.time.equals(time);
    }
}
