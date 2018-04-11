package sheron.csci4100u.labs.lab7;


class Grade {
    private int    studentId;
    private String courseComponent;
    private float  mark;


    Grade(int studentId, String courseComponent, float mark) {
        this.studentId       = studentId;
        this.courseComponent = courseComponent;
        this.mark            = mark;
    }


    int getStudentId() {
        return studentId;
    }


    String getCourseComponent() {
        return courseComponent;
    }


    float getMark() {
        return mark;
    }
}
