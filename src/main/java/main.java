import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import lab2.registration.model.*;
import lab2.registration.reader.CourseInfoDataReader;
import lab2.registration.reader.StudentDataReader;
import lab2.registration.service.*;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {

        try {
            long studentId = 102;
            long courseId = 10123;
            long randomInstance = 100003;

            Multimap<Long, Long> subscribedStudents = ArrayListMultimap.create();
            subscribedStudents.put(studentId, randomInstance);
            System.out.println("Текущие подписки (студент, курс): " + subscribedStudents.entries() + "\n");

            StudentService studentService = new StudentServiceImpl();
            CourseInstructorService courseInstructorService = new CourseInstructorServiceImpl();

            // when
            studentService.subscribe(studentId, courseId, subscribedStudents);
            System.out.println("Текущие подписки (студент, курс): " + subscribedStudents.entries() + "\n");

            studentService.unsubscribe(studentId, courseId, subscribedStudents);
            System.out.println("Текущие подписки (студент, курс): " + subscribedStudents.entries() + "\n");

            List<CourseInstance> coursesByStudentId =
                    studentService.findAllSubscriptionsByStudentId(studentId, subscribedStudents);
            System.out.println("Студент " + studentId + " подписан на курсы:");
            for (CourseInstance courseInstance : coursesByStudentId) {
                System.out.println(courseInstance.getCourseId());
            }
            System.out.println();

            Student[] studentsSubscribedOnCourseId =
                    courseInstructorService.findStudentsByCourseId(10234, subscribedStudents);
            System.out.println("На курс " + 10234 + " подписаны студенты:");
            for (Student student : studentsSubscribedOnCourseId) {
                System.out.println(student.getId());
            }
            System.out.println();

            long instructorId = 9003;
            Student[] studentsSubscribedOnCourseByInstructor =
                    courseInstructorService.findStudentsByInstructorId(instructorId, subscribedStudents);
            System.out.println("На курсы преподавателя " + instructorId + " подписаны студенты:");
            for (Student student : studentsSubscribedOnCourseByInstructor) {
                System.out.println(student.getId());
            }
            System.out.println();

            courseId = 10234;
            Instructor[] instructors = courseInstructorService.findReplacement(instructorId, courseId);
            System.out.println("Заменить преподавателя " + instructorId + " могут: ");
            for (Instructor instructor : instructors) {
                System.out.println(instructor.getId());
            }
            System.out.println();

        } catch (IOException e){
            e.printStackTrace();
        }

    }

}