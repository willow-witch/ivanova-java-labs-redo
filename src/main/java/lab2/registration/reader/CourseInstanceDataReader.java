package lab2.registration.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab2.registration.model.CourseInstance;

import java.io.File;
import java.io.IOException;

public class CourseInstanceDataReader {

    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * @return список студентов-бакалавров
     */
    public CourseInstance[] readCourseInstanceData() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(new File("src/main/resources/courseInstances.json"), CourseInstance[].class);
    }
}
