package com.test.profile;

import com.test.profile.dto.StudentDto;
import com.test.profile.entity.Student;
import com.test.profile.handler.exceptions.ResourceNotFoundException;
import com.test.profile.mapper.StudentMapper;
import com.test.profile.repository.StudentRepository;
import com.test.profile.requests.CreateStudentRequest;
import com.test.profile.requests.UpdateStudentRequest;
import com.test.profile.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getStudentsWhenNoStudents() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        List<StudentDto> result = studentService.getStudents();

        assertEquals(0, result.size());
    }

    @Test
    void getStudentsWhenOneStudent() {
        Student student = new Student(UUID.randomUUID(), "testName", "testEmail");
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentDto> result = studentService.getStudents();
        assertEquals(1, result.size());
    }

    @Test
    void getStudentsWhenMultipleStudents() {
        Student student1 = new Student(UUID.randomUUID(), "testName1", "testEmail1");
        Student student2 = new Student(UUID.randomUUID(), "testName2", "testEmail2");
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<StudentDto> result = studentService.getStudents();

        assertEquals(2, result.size());
    }

    @Test
    void createStudentReturnsStudentDto() {
        UUID randomUUID = UUID.randomUUID();
        CreateStudentRequest request = new CreateStudentRequest("testName", "testEmail");
        Student student = new Student(randomUUID, "testName", "testEmail");
        StudentDto expectedDto = new StudentDto(randomUUID, "testName", "testEmail");

        when(studentMapper.createStudentRequestToStudent(any(CreateStudentRequest.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.studentToStudentDTO(any(Student.class))).thenReturn(expectedDto);

        StudentDto actualDto = studentService.createStudent(request);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void updateStudentWithValidIdShouldReturnUpdatedStudentDto() {
        UUID id = UUID.randomUUID();
        UpdateStudentRequest updateRequest = new UpdateStudentRequest("updatedName", "updatedEmail");
        Student existingStudent = new Student(id, "originalName", "originalEmail");
        Student updatedStudent = new Student(id, updateRequest.getName(), updateRequest.getEmail());
        StudentDto expectedDto = new StudentDto(id, updateRequest.getName(), updateRequest.getEmail());

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        when(studentMapper.studentToStudentDTO(any(Student.class))).thenReturn(expectedDto);

        StudentDto actualDto = studentService.updateStudent(id, updateRequest);

        assertEquals(expectedDto, actualDto);
        assertEquals("updatedName", actualDto.getName());
        assertEquals("updatedEmail", actualDto.getEmail());
    }

    @Test
    void updateStudentWithNonExistingIdShouldThrowResourceNotFoundException() {
        UUID nonExistingId = UUID.randomUUID();
        UpdateStudentRequest updateRequest = new UpdateStudentRequest("testName", "updatedEmail");

        when(studentRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.updateStudent(nonExistingId, updateRequest);
        });

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudentWithExistingIdShouldDeleteStudent() {
        UUID existingId = UUID.randomUUID();
        Student existingStudent = new Student(existingId, "testName", "testEmail");

        when(studentRepository.findById(existingId)).thenReturn(Optional.of(existingStudent));

        studentService.deleteStudent(existingId);

        verify(studentRepository).deleteById(existingId);
    }

    @Test
    void deleteStudentWithNonExistingIdShouldThrowResourceNotFoundException() {
        UUID nonExistingId = UUID.randomUUID();

        when(studentRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent(nonExistingId);
        });

        verify(studentRepository, never()).deleteById(nonExistingId);
    }
}
