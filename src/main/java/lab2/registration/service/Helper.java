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
    public Student findStudentByStudentId(long studentId){
        Student targetStudent = new Student();

        try{
            StudentDataReader studentDataReader = new StudentDataReader();
            Student[] bachelorStudents = studentDataReader.readBachelorStudentData();
            Student[] masterStudents = studentDataReader.readMasterStudentData();


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
        } catch (IOException e){
            e.printStackTrace();
        }


        return targetStudent;
    }

    /**
     * Поиск CourseInstance по courseId
     *
     * @param courseId идентификатор курса
     * @return объект CourseInstance с данным courseId
     */
    public CourseInstance findCourseInstanceByCourseId(long courseId) {

        CourseInstance targetCourseInstance = new CourseInstance();
        try{
            CourseInstanceDataReader instanceReader = new CourseInstanceDataReader();
            CourseInstance[] courseInstances = instanceReader.readCourseInstanceData();
            List<CourseInstance> possibleInstances = new ArrayList<>();


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
        } catch (IOException e){
            e.printStackTrace();
        }


        return targetCourseInstance;
    }

    /**
     * Поиск CourseInstance по instanceId
     *
     * @param instanceId идентификатор курса
     * @return объект CourseInstance с данным courseId
     */
    public CourseInstance findCourseInstanceByInstanceId(long instanceId) {

        CourseInstance targetCourseInstance = new CourseInstance();

        try {
            CourseInstanceDataReader instanceReader = new CourseInstanceDataReader();
            CourseInstance[] courseInstances = instanceReader.readCourseInstanceData();


            LocalDate now = LocalDate.now();

            for(int i=0; i<courseInstances.length; i++){
                if (courseInstances[i].getId() == instanceId){
                    targetCourseInstance = courseInstances[i];
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return targetCourseInstance;
    }


    /**
     * Поиск CourseInfo по courseId
     *
     * @param courseId идентификатор курса
     * @return объект CourseInfo с данным courseId
     */
    public CourseInfo findCourseInfoByCourseId(long courseId) {

        CourseInfo targetCourseInfo = new CourseInfo();

        try {
            CourseInfoDataReader infoReader = new CourseInfoDataReader();
            CourseInfo[] courseInfos = infoReader.readCourseInfoData();


            for(int i=0; i<courseInfos.length; i++){
                if (courseInfos[i].getId() == courseId){
                    targetCourseInfo = courseInfos[i];
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        return targetCourseInfo;
    }

    public Instructor findInstructorByInstructorId(long instructorId) {

        Instructor targetInstructor = new Instructor();

        try {
            InstructorDataReader instructorDataReader = new InstructorDataReader();
            Instructor[] instructors = instructorDataReader.readInstructorData();


            for(int i=0; i<instructors.length; i++){
                if (instructors[i].getId() == instructorId){
                    targetInstructor = instructors[i];
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        return targetInstructor;
    }

    public List<Long> findStudentSubscribedOnCourse(long instanceId, Multimap<Long, Long> studentsSubscribedOnCourses) {

        List<Long> result = new ArrayList<>();

        studentsSubscribedOnCourses.forEach((student, instance)-> {
            if (instance == instanceId){
                result.add(student);
            }
        });

        return result;
    }


}
