package com.example.monday.resource;

import com.example.monday.data.Student;
import com.example.monday.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/students")
@RequiredArgsConstructor
public class StudentResource {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudents(@Validated @RequestBody CreateStudent createStudent){
        studentService.saveStudent(createStudent);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable UUID id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public List<StudentDto> getStudentByName(@RequestParam String name){
        return studentService.getStudentsByName(name);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(String name){
        studentService.deleteByName(name);
    }
}
