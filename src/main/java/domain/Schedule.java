package domain;

import java.time.DayOfWeek;


public class Schedule {
    private static final int CONFIRMATION_TOLERANCE = 0;

    private DayOfWeek day;
    private Integer time;

    public Schedule(DayOfWeek day, Integer time) {
        this.day = day;
        this.time = time;
    }


    public boolean accepts(Schedule aTime) {
        return aTime.day == day && acceptsTimeWithTolerance(aTime.time);
    }

    private boolean acceptsTimeWithTolerance(Integer time) {
        return
                time.equals(this.time) ||
                time.equals(this.time - CONFIRMATION_TOLERANCE) ||
                time.equals(this.time + CONFIRMATION_TOLERANCE);
    }

    public boolean isTheSameAs(Schedule anotherSchedule) {
        return anotherSchedule.day == day && anotherSchedule.time.equals(time);
    }
}
