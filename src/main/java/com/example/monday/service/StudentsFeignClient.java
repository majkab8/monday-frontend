package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.resource.CreateStudent;
import com.example.monday.resource.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name="studentsClient", url ="http://localhost:8080/students")
public interface StudentsFeignClient {

    @PostMapping
    Student saveStudents(@Validated @RequestBody CreateStudent createStudent);

    @GetMapping("/{id}")
    StudentDto getStudentById(@PathVariable UUID id);
}
