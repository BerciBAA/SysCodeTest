package com.test.profile.service;

import com.test.profile.dto.StudentDto;
import com.test.profile.entity.Student;
import com.test.profile.handler.exceptions.ResourceNotFoundException;
import com.test.profile.mapper.StudentMapper;
import com.test.profile.repository.StudentRepository;
import com.test.profile.requests.CreateStudentRequest;
import com.test.profile.requests.UpdateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    @Override
    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentMapper.INSTANCE.usersToStudentDTOs(students);
    }

    @Override
    public StudentDto createStudent(CreateStudentRequest createStudentRequest) {
        Student student = StudentMapper.INSTANCE.createStudentRequestToStudent(createStudentRequest);
        Student savedStudent = studentRepository.save(student);
        logger.info("Student created with ID: {}", savedStudent.getId());
        return  StudentMapper.INSTANCE.studentToStudentDTO(savedStudent);
    }

    @Override
    public StudentDto updateStudent(UUID id, UpdateStudentRequest updateStudentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to update: Student not found with ID: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });

        student.setName(updateStudentRequest.getName());
        student.setEmail(updateStudentRequest.getEmail());
        Student updatedStudent = studentRepository.save(student);
        logger.info("Student with ID: {} updated", id);
        return StudentMapper.INSTANCE.studentToStudentDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(UUID id) {
        studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to update: Student not found with ID: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });

        studentRepository.deleteById(id);
        logger.info("Student with ID: {} deleted", id);
    }
}
