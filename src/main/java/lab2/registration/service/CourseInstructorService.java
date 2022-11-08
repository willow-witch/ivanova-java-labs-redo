package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.Instructor;
import lab2.registration.model.Student;

import java.io.IOException;

/**
 * Интерфейс сервиса для преподавателя
 */
public interface CourseInstructorService {
    
    /**
     * @param courseId идентификатор курса
     * @param studentsSubscribedOnCourses текущие подписки студентов на курсы
     * @return список студентов, зарегистрированных на данный курс
     */
    Student[] findStudentsByCourseId(long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException;

    /**
     * @param instructorId идентификатор преподавателя
     * @param studentsSubscribedOnCourses текущие подписки студентов на курсы
     * @return список студентов, посещающих один из курсов данного преподавателя
     */
    Student[] findStudentsByInstructorId(long instructorId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException;

    /**
     * @param instructorId идентификатор преподавателя
     * @param courseId идентификатор курса
     * @return список преподавателей, которые могут прочитать данный курс вместо данного преподавателя
     */
    Instructor[] findReplacement(long instructorId, long courseId) throws IOException;

}
