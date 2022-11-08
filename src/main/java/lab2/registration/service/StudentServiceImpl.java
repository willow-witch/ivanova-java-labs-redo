package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final LoggerService LOGGER_SERVICE = new LoggerService("StudentServiceLog");

    @Override
    public WrapperClass subscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) {

        try {
            CourseInstance targetCourseInstance;
            CourseInfo targetCourseInfo;
            Student targetStudent;
            Helper helper = new Helper();

            targetCourseInstance = helper.findCourseInstanceByCourseId(courseId);
            targetStudent = helper.findStudentByStudentId(studentId);
            targetCourseInfo = helper.findCourseInfoByCourseId(courseId);

            if (targetStudent == null) {
                throw new ExceptionService("Невозможно записать студента. Нет такого студента!", "subscribe");
            }

            if (targetCourseInfo == null || targetCourseInstance == null) {
                throw new ExceptionService("Невозможно записать студента. Нет такого курса!", "subscribe");
            }

            // начался ли курс?
            LocalDate now = LocalDate.now();
            if ((targetCourseInstance.getStartDate() == null) || (now.isAfter(targetCourseInstance.getStartDate()))) {
                throw new ExceptionService("Невозможно записать студента. Курс уже начался!", "subscribe");
            }

            // правильная ли программа обучения?
            if (targetCourseInfo.getStudentCategories() != null) {
                if (Arrays.stream(targetCourseInfo.getStudentCategories())
                        .noneMatch(temp -> temp == targetStudent.getStudentCategory())) {
                    throw new ExceptionService("Невозможно записать студента. Неверная программа обучения!", "subscribe");
                }
            }

            // пройдены ли необходимые курсы?
            if (targetCourseInfo.getPrerequisites() != null) {
                if (Arrays.stream(targetStudent.getCompletedCourses()).noneMatch(
                        completeCourse -> Arrays.stream(targetCourseInfo.getPrerequisites()).anyMatch(
                                predCourse -> completeCourse == predCourse))) {
                    throw new ExceptionService(
                            "Невозможно записать студента. Студент не прошел необходимые дополнительные курсы!",
                            "subscribe");
                }
            }

            // не записан ли уже студент на данный курс?
            List list = new ArrayList(studentsSubscribedOnCourses.get(studentId));
            if (list.contains(courseId)) {
                throw new ExceptionService("Невозможно записать студента. Студент уже записан!", "subscribe");
            }

            // не заполнен ли курс?
            if ((targetCourseInstance.getCapacity() != 0)
                    && (targetCourseInstance.getCapacity() >
                    helper.findStudentSubscribedOnCourse(courseId, studentsSubscribedOnCourses).size())) {
                throw new ExceptionService("Невозможно записать студента. Курс заполнен!", "subscribe");
            }

            studentsSubscribedOnCourses.put(studentId, targetCourseInstance.getId());
            LOGGER_SERVICE.WriteLogsInfo("Студент " + studentId + " записан на курс " + courseId + "!");

            return new WrapperClass(ActionStatus.OK, studentsSubscribedOnCourses);
        } catch (ExceptionService e){
            e.WriteLogs();
            LOGGER_SERVICE.WriteLogsInfo("Ошибка записи на курс! Подробнее в: lab2/Logs/ExceptionService/ExceptionLog");

            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }
    }

    @Override
    public WrapperClass unsubscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) {

        try {
            Helper helper = new Helper();
            CourseInstance targetCourseInstance = helper.findCourseInstanceByCourseId(courseId);

            LocalDate now = LocalDate.now();

            if (targetCourseInstance.getStartDate() == null) {
                throw new ExceptionService(
                        "Невозможно отписать студента " + studentId + ". Нет курса с ID " + courseId + "!",
                        "unsubscribe");
            }

            if (now.isAfter(targetCourseInstance.getStartDate())) {
                throw new ExceptionService(
                        "Невозможно отписать студента  " + studentId + ". Курс " + courseId + " уже начался!",
                        "unsubscribe");
            }

            studentsSubscribedOnCourses.remove(studentId, targetCourseInstance.getId());
            LOGGER_SERVICE.WriteLogsInfo("Студент " + studentId + " отписан от курса " + courseId + "!");

            return new WrapperClass(ActionStatus.OK, studentsSubscribedOnCourses);
        } catch (ExceptionService e){
            e.WriteLogs();
            LOGGER_SERVICE.WriteLogsInfo("Ошибка при отписке от курса! Подробнее в: lab2/Logs/ExceptionService/ExceptionLog");

            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }
    }

    @Override
    public List<CourseInstance> findAllSubscriptionsByStudentId(long studentId, Multimap<Long, Long> studentsSubscribedOnCourses) {

        Helper helper = new Helper();
        List<CourseInstance> courseInstances = new ArrayList<>();

        try {
            List<Long> list = new ArrayList(studentsSubscribedOnCourses.get(studentId));

            if (list.size() == 0) {
                throw new ExceptionService(
                        "Студент  " + studentId + "не подписан ни на какие курсы!",
                        "findAllSubscriptionsByStudentId");
            }

            list.forEach((instanceId) -> {
                courseInstances.add(helper.findCourseInstanceByInstanceId(instanceId));
            });
            LOGGER_SERVICE.WriteLogsInfo("Студент " + studentId + " подписан на курсы:" + list);

        } catch (ExceptionService e){
            e.WriteLogs();
            LOGGER_SERVICE.WriteLogsInfo("Ошибка поиска курсов! Подробнее в: lab2/Logs/ExceptionService/ExceptionLog");
        }

        return courseInstances;
    }
}
