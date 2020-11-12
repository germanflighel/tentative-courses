package domain;

import java.time.DayOfWeek;

public class Schedule {

    private DayOfWeek day;
    private Integer time;

    public Schedule(DayOfWeek day, Integer time) {
        this.day = day;
        this.time = time;
    }

    public boolean accepts(Schedule aTime) {     // This message can be modified to include the tolerance policy
        return aTime.day == day && aTime.time.equals(time);
    }
}
