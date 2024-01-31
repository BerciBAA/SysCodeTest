package com.test.profile.mapper;

import com.test.profile.dto.StudentDto;
import com.test.profile.entity.Student;
import com.test.profile.requests.CreateStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    StudentDto studentToStudentDTO(Student student);
    Student createStudentRequestToStudent(CreateStudentRequest createStudentRequest);
    List<StudentDto> usersToStudentDTOs(List<Student> students);
}
