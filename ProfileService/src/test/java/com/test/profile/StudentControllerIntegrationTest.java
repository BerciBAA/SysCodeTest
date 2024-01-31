package com.test.profile;

import com.test.profile.entity.Student;
import com.test.profile.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    private UUID preLoadedStudentId;

    @BeforeEach
    public void setup() {
        Student preLoadedStudent = studentRepository.save(new Student(null, "Initial Name", "initial@example.com"));
        preLoadedStudentId = preLoadedStudent.getId();
    }

    @Test
    public void testCreateStudent() throws Exception {
        String studentJson = "{\"name\":\"John Doe\",\"email\":\"johndoe@example.com\"}";

        mockMvc.perform(post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetStudents() throws Exception {
        mockMvc.perform(get("/api/student/get-students"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        String studentJson = "{\"name\":\"Jane Doe\",\"email\":\"janedoe@example.com\"}";

        mockMvc.perform(put("/api/student/update/" + preLoadedStudentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/student/delete/" + preLoadedStudentId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createStudentWithInvalidDataShouldReturnBadRequest() throws Exception {
        String invalidStudentJson = "{\"name\":\"\"}";

        mockMvc.perform(post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidStudentJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").isNotEmpty());

    }
}

