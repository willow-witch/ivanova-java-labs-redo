package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public WrapperClass subscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses)
            throws IOException {

        CourseInstance targetCourseInstance;
        CourseInfo targetCourseInfo;
        Student targetStudent;
        Helper helper = new Helper();

        targetCourseInstance = helper.findCourseInstanceByCourseId(courseId);
        targetStudent = helper.findStudentByStudentId(studentId);
        targetCourseInfo = helper.findCourseInfoByCourseId(courseId);

        if (targetStudent == null){
            System.out.println("Невозможно записать студента " + studentId +". Нет такого студента!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        if (targetCourseInfo == null || targetCourseInstance == null){
            System.out.println("Невозможно записать студента " + studentId +". Нет курса с ID " + courseId +"!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        // начался ли курс?
        LocalDate now = LocalDate.now();
        if ((targetCourseInstance.getStartDate() == null) || (now.isAfter(targetCourseInstance.getStartDate()))){
            System.out.println("Невозможно записать студента " + studentId +". Курс " + courseId +" уже начался!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        // правильная ли программа обучения?
        if (targetCourseInfo.getStudentCategories() != null){
            if (Arrays.stream(targetCourseInfo.getStudentCategories())
                    .noneMatch(temp -> temp == targetStudent.getStudentCategory())){
                System.out.println("Невозможно записать студента " + studentId +". Неверная программа обучения!");
                return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
            }
        }

        // пройдены ли необходимые курсы?
        if (targetCourseInfo.getPrerequisites() != null) {
            if(Arrays.stream(targetStudent.getCompletedCourses()).noneMatch(
                    completeCourse-> Arrays.stream(targetCourseInfo.getPrerequisites()).anyMatch(
                            predCourse->completeCourse == predCourse))) {
                System.out.println("Невозможно записать студента " + studentId +". Студент не прошел необходимые дополнительные курсы!");
                return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
            }
        }

        // не записан ли уже студент на данный курс?
        List list = new ArrayList(studentsSubscribedOnCourses.get(studentId));
        if (list.contains(courseId)){
            System.out.println("Невозможно записать студента " + studentId +". Студент уже записан!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        // не заполнен ли курс?
        if ((targetCourseInstance.getCapacity() != 0)
            && (targetCourseInstance.getCapacity() >
                helper.findStudentSubscribedOnCourse(courseId, studentsSubscribedOnCourses).size())){
            System.out.println("Невозможно записать студента " + studentId +". Курс " + courseId + " заполнен!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        studentsSubscribedOnCourses.put(studentId, targetCourseInstance.getId());
        System.out.println("Студент " + studentId +" записан на курс " + courseId+ "!");

        return new WrapperClass(ActionStatus.OK, studentsSubscribedOnCourses);
    }

    @Override
    public WrapperClass unsubscribe(long studentId, long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException {

        Helper helper = new Helper();
        CourseInstance targetCourseInstance = helper.findCourseInstanceByCourseId(courseId);

        LocalDate now = LocalDate.now();

        if (targetCourseInstance.getStartDate() == null){
            System.out.println("Невозможно отписать студента " + studentId + ". Нет курса с ID " + courseId +"!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        if (now.isAfter(targetCourseInstance.getStartDate())){
            System.out.println("Невозможно отписать студента  " + studentId + ". Курс " + courseId +" уже начался!");
            return new WrapperClass(ActionStatus.NOK, studentsSubscribedOnCourses);
        }

        studentsSubscribedOnCourses.remove(studentId, targetCourseInstance.getId());
        System.out.println("Студент " + studentId +" отписан от курса " + courseId + "!");

        return new WrapperClass(ActionStatus.OK, studentsSubscribedOnCourses);
    }

    @Override
    public List<CourseInstance> findAllSubscriptionsByStudentId(long studentId, Multimap<Long, Long> studentsSubscribedOnCourses) {

        Helper helper = new Helper();
        List<CourseInstance> courseInstances = new ArrayList<>();
        List<Long> list = new ArrayList(studentsSubscribedOnCourses.get(studentId));

        list.forEach((instanceId)->{
            try {
                courseInstances.add(helper.findCourseInstanceByInstanceId(instanceId));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return courseInstances;
    }
}
