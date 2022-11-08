package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.*;
import lab2.registration.reader.CourseInfoDataReader;
import lab2.registration.reader.CourseInstanceDataReader;
import lab2.registration.reader.InstructorDataReader;
import lab2.registration.reader.StudentDataReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    /**
     * Поиск студента по его studentId
     *
     * @param studentId идентификатор студента
     * @return объект Student с данным studentId
     */
    public Student findStudentByStudentId(long studentId) throws IOException {

        StudentDataReader studentDataReader = new StudentDataReader();
        Student[] bachelorStudents = studentDataReader.readBachelorStudentData();
        Student[] masterStudents = studentDataReader.readMasterStudentData();
        Student targetStudent = new Student();

        for(int i=0; i<bachelorStudents.length; i++){
            if (bachelorStudents[i].getId() == studentId){
                targetStudent = bachelorStudents[i];
                targetStudent.setStudentCategory(StudentCategory.BACHELOR);
            }
        }
        for(int i=0; i<masterStudents.length; i++){
            if (masterStudents[i].getId() == studentId){
                targetStudent = masterStudents[i];
                targetStudent.setStudentCategory(StudentCategory.MASTER);
            }
        }

        return targetStudent;
    }

    /**
     * Поиск CourseInstance по courseId
     *
     * @param courseId идентификатор курса
     * @return объект CourseInstance с данным courseId
     */
    public CourseInstance findCourseInstanceByCourseId(long courseId) throws IOException {

        CourseInstanceDataReader instanceReader = new CourseInstanceDataReader();
        CourseInstance[] courseInstances = instanceReader.readCourseInstanceData();
        List<CourseInstance> possibleInstances = new ArrayList<>();
        CourseInstance targetCourseInstance = new CourseInstance();

        LocalDate now = LocalDate.now();

        for(int i=0; i<courseInstances.length; i++){
            if (courseInstances[i].getCourseId() == courseId){
                possibleInstances.add(courseInstances[i]);
            }
        }

        if (possibleInstances.stream().filter(i -> i.getStartDate().isAfter(now)).toArray().length != 0){
            targetCourseInstance = possibleInstances.stream().filter(i -> i.getStartDate().isAfter(now)).findFirst().get();
        } else {
            targetCourseInstance = possibleInstances.stream().findFirst().get();
        }

        return targetCourseInstance;
    }

    /**
     * Поиск CourseInstance по instanceId
     *
     * @param instanceId идентификатор курса
     * @return объект CourseInstance с данным courseId
     */
    public CourseInstance findCourseInstanceByInstanceId(long instanceId) throws IOException {

        CourseInstanceDataReader instanceReader = new CourseInstanceDataReader();
        CourseInstance[] courseInstances = instanceReader.readCourseInstanceData();
        CourseInstance targetCourseInstance = new CourseInstance();

        LocalDate now = LocalDate.now();

        for(int i=0; i<courseInstances.length; i++){
            if (courseInstances[i].getId() == instanceId){
                targetCourseInstance = courseInstances[i];
            }
        }

        return targetCourseInstance;
    }


    /**
     * Поиск CourseInfo по courseId
     *
     * @param courseId идентификатор курса
     * @return объект CourseInfo с данным courseId
     */
    public CourseInfo findCourseInfoByCourseId(long courseId) throws IOException {

        CourseInfoDataReader infoReader = new CourseInfoDataReader();
        CourseInfo[] courseInfos = infoReader.readCourseInfoData();
        CourseInfo targetCourseInfo = new CourseInfo();


        for(int i=0; i<courseInfos.length; i++){
            if (courseInfos[i].getId() == courseId){
                targetCourseInfo = courseInfos[i];
            }
        }

        return targetCourseInfo;
    }

    public Instructor findInstructorByInstructorId(long instructorId) throws IOException {

        InstructorDataReader instructorDataReader = new InstructorDataReader();
        Instructor[] instructors = instructorDataReader.readInstructorData();
        Instructor targetInstructor = new Instructor();

        for(int i=0; i<instructors.length; i++){
            if (instructors[i].getId() == instructorId){
                targetInstructor = instructors[i];
            }
        }

        return targetInstructor;
    }

    public List<Long> findStudentSubscribedOnCourse(long instanceId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException {

        List<Long> result = new ArrayList<>();

        studentsSubscribedOnCourses.forEach((student, instance)-> {
            if (instance == instanceId){
                result.add(student);
            }
        });

        return result;
    }


}
