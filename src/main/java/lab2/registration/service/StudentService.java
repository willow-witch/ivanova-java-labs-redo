package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.CourseInstance;
import lab2.registration.model.WrapperClass;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс сервиса для студентов
 */
public interface StudentService {

    /**
     * Регистрация студента на курс. Регистрация возможна при следующих условиях:
     * - курс еще не начался;
     * - курс предназначен для категории данного студента (магистра/бакалавра);
     * - студент прошел все обязательные курсы, необходимые для посещения данного курса;
     * - в курсе есть свободные места.
     *
     * @param studentId идентификатор студента
     * @param courseId идентификатор курса, соответствующий CourseInstance.id
     * @param studentsSubscribedOnCourses текущие подписки студентов
     * @return результат выполнения регистрации + обновленные подписки студентов
     */
    WrapperClass subscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException;

    /**
     * Отмена регистрации студента на курс, которая возможна только в том случае, когда
     * курс еще не начался.
     *
     * @param studentId идентификатор студента
     * @param courseId идентификатор курса, соответствующий CourseInstance.id
     * @param studentsSubscribedOnCourses текущие подписки студентов
     * @return результат выполнения отмены регистрации + обновленные подписки студентов
     */
    WrapperClass unsubscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException;

    /**
     * @param studentId идентификатор студента
     * @return список всех курсов, на которые записан студент
     */
    List<CourseInstance> findAllSubscriptionsByStudentId(long studentId, Multimap<Long, Long> studentsSubscribedOnCourses) throws ExceptionService;
    
}
