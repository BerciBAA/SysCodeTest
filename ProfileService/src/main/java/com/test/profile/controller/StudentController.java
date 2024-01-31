package com.test.profile.controller;

import com.test.profile.dto.StudentDto;
import com.test.profile.requests.CreateStudentRequest;
import com.test.profile.requests.UpdateStudentRequest;
import com.test.profile.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "Student", description = "API endpoints related to student operations")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @PostMapping("/create")
    @Operation(summary = "Create a new student", description = "Creates a new student with the given details")
    @ApiResponse(responseCode = "201", description = "Student created", content = @Content(schema = @Schema(implementation = StudentDto.class)))
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){
        StudentDto createdStudent = studentService.createStudent(createStudentRequest);
        logger.info("Creating new student with name: {}", createStudentRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/get-students")
    @Operation(summary = "List all students", description = "Returns a list of all students")
    public ResponseEntity<List<StudentDto>> getStudents(){
        return ResponseEntity.ok(studentService.getStudents());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a student's data", description = "Updates the data of a student with the specified ID")
    @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(schema = @Schema(implementation = StudentDto.class)))
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") @Parameter(description = "ID of the student to be updated") UUID id, @Valid @RequestBody UpdateStudentRequest updateStudentRequest) {
        StudentDto updatedStudent = studentService.updateStudent(id, updateStudentRequest);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a student", description = "Deletes the student with the specified ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") @Parameter(description = "ID of the student to be deleted") UUID id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
