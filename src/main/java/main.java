import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lab2.registration.model.CourseInstance;
import lab2.registration.service.CourseInstructorService;
import lab2.registration.service.CourseInstructorServiceImpl;
import lab2.registration.service.StudentService;
import lab2.registration.service.StudentServiceImpl;

import java.io.IOException;
import java.util.List;

public class main {

    public static void main(String[] args) {

        long studentId = 102;
        long courseId = 10123;
        long randomInstance = 100003;

        Multimap<Long, Long> subscribedStudents = ArrayListMultimap.create();
        subscribedStudents.put(studentId, randomInstance);

        StudentService studentService = new StudentServiceImpl();
        CourseInstructorService courseInstructorService = new CourseInstructorServiceImpl();

        try {
            studentService.subscribe(studentId, courseId, subscribedStudents);
            studentService.unsubscribe(studentId, courseId, subscribedStudents);

            List<CourseInstance> coursesByStudentId =
                    studentService.findAllSubscriptionsByStudentId(studentId, subscribedStudents);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
