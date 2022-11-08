package lab2.registration.service;

import com.google.common.collect.Multimap;
import lab2.registration.model.Instructor;
import lab2.registration.model.Student;
import lab2.registration.reader.InstructorDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseInstructorServiceImpl implements CourseInstructorService {
    @Override
    public Student[] findStudentsByCourseId(long courseId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException {

        Helper helper = new Helper();
        long instanceId = helper.findCourseInstanceByCourseId(courseId).getId();

        List<Student> resultList = new ArrayList<>();
        List<Long> subscribedStudentsIds =
                helper.findStudentSubscribedOnCourse(instanceId, studentsSubscribedOnCourses);

        subscribedStudentsIds.forEach((studentId) -> {
            try {
                resultList.add(helper.findStudentByStudentId(studentId));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return resultList.toArray(new Student[0]);
    }

    @Override
    public Student[] findStudentsByInstructorId(long instructorId, Multimap<Long, Long> studentsSubscribedOnCourses) throws IOException {

        Helper helper = new Helper();
        List<Long> instructorCoursesList = Arrays.stream
                (helper.findInstructorByInstructorId(instructorId).getCanTeach()).boxed().toList();

        List<Student> studentsList = new ArrayList<>();

        instructorCoursesList.forEach((course) -> {
            studentsSubscribedOnCourses.forEach((studentId, instanceId) -> {
                try {
                    long courseId = helper.findCourseInstanceByInstanceId(instanceId).getCourseId();
                    if (course == courseId){
                        studentsList.add(helper.findStudentByStudentId(studentId));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });


        return studentsList.toArray(new Student[0]);
    }

    @Override
    public Instructor[] findReplacement(long instructorId, long courseId) throws IOException {

        InstructorDataReader instructorDataReader = new InstructorDataReader();
        List<Instructor> instructors = Arrays.asList(instructorDataReader.readInstructorData());
        List<Instructor> list = new ArrayList<>();

        instructors.forEach(instructor -> {
            List<Long> whatCanTeach = Arrays.stream(instructor.getCanTeach()).boxed().toList();

            if((instructor.getId() != instructorId) && (whatCanTeach.contains(courseId))){
                list.add(instructor);
            }
        });

        return list.toArray(new Instructor[0]);
    }
}
