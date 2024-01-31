package com.test.profile.service;

import com.test.profile.dto.StudentDto;
import com.test.profile.requests.CreateStudentRequest;
import com.test.profile.requests.UpdateStudentRequest;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    List<StudentDto> getStudents();
    StudentDto createStudent(CreateStudentRequest addProfileRequest);
    StudentDto updateStudent(UUID uuid, UpdateStudentRequest modifyProfileRequest);
    void deleteStudent(UUID uuid);
}
