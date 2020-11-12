package domain;

public class Enrollment {
    private Student student;
    private Boolean isConfirmed;


    public Enrollment(Student student, Boolean isConfirmed) {
        this.student = student;
        this.isConfirmed = isConfirmed;
    }

    public Student getStudent() {
        return student;
    }
}
