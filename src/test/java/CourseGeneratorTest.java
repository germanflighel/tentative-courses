import domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseGeneratorTest {

    @Test
    public void noIndividualCourseAreGenerated() {

        Schedule schedule = new Schedule(DayOfWeek.MONDAY, 11);
        Schedule scheduleTeacher = new Schedule(DayOfWeek.FRIDAY, 13);
        List<Schedule> availables = new ArrayList<Schedule>(Arrays.asList(schedule));
        List<Schedule> availablesTeacher = new ArrayList<Schedule>(Arrays.asList(scheduleTeacher));

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(availablesTeacher)));
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Modality.INDIVIDUAL, Level.ADVANCED, availables)));


        CourseGenerator courseGenerator = new CourseGenerator(teachers, students);

        List<Course> allCourses = courseGenerator.generateAll();

        Assert.assertEquals(0, allCourses.size());
    }

    @Test
    public void anIndividualCourseIsGenerated() {

        Schedule schedule = new Schedule(DayOfWeek.MONDAY, 11);
        List<Schedule> availables = new ArrayList<Schedule>(Arrays.asList(schedule));

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(availables)));
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Modality.INDIVIDUAL, Level.ADVANCED, availables)));


        CourseGenerator courseGenerator = new CourseGenerator(teachers, students);

        List<Course> allCourses = courseGenerator.generateAll();

        Assert.assertEquals(1, allCourses.size());
    }

    @Test
    public void anIndividualCourseIsGeneratedv2() {

        Schedule schedule = new Schedule(DayOfWeek.MONDAY, 11);
        List<Schedule> availables = new ArrayList<Schedule>(Arrays.asList(schedule));

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(availables), new Teacher(availables)));
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Modality.INDIVIDUAL, Level.ADVANCED, availables)));


        CourseGenerator courseGenerator = new CourseGenerator(teachers, students);

        List<Course> allCourses = courseGenerator.generateAll();

        Assert.assertEquals(2, allCourses.size());
    }

    @Test
    public void anIndividualCourseIsGeneratedv3() {

        Schedule schedule = new Schedule(DayOfWeek.MONDAY, 11);
        List<Schedule> availables = new ArrayList<Schedule>(Arrays.asList(schedule));

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(availables), new Teacher(availables), new Teacher(availables)));
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Modality.INDIVIDUAL, Level.ADVANCED, availables)));


        CourseGenerator courseGenerator = new CourseGenerator(teachers, students);

        List<Course> allCourses = courseGenerator.generateAll();

        Assert.assertEquals(3, allCourses.size());
    }


    @Test
    public void noRepeatedCourses() {

        Schedule schedule = new Schedule(DayOfWeek.MONDAY, 11);
        List<Schedule> availables = new ArrayList<Schedule>(Arrays.asList(schedule));

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(availables)));
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Modality.GROUP, Level.ADVANCED, availables), new Student(Modality.GROUP, Level.ADVANCED, availables)));


        CourseGenerator courseGenerator = new CourseGenerator(teachers, students);

        List<Course> allCourses = courseGenerator.generateAll();

        Assert.assertEquals(3, allCourses.size());
    }


}
