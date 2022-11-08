package lab2.registration.model;

/**
 * Класс для информации о студенте
 */
public class Student extends Person {

    /**
     * список идентификаторов курсов (CourseInstance.id), пройденных студентом
     */
    private long[] completedCourses;

    public StudentCategory getStudentCategory() {
        return studentCategory;
    }

    public void setStudentCategory(StudentCategory studentCategory) {
        this.studentCategory = studentCategory;
    }

    private StudentCategory studentCategory;

    public long[] getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(long[] completedCourses) {
        this.completedCourses = completedCourses;
    }
}
