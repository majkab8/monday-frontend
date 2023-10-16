package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        var index = createIndex(student.unit());
        var toSave = new Student(student.id(), student.name(), student.unit(), index);
        studentRepository.saveStudent(toSave);
        return toSave;
    }

    public Student getStudentById(UUID id){
        return studentRepository.getStudentById(id);
    }

    private Long createIndex(StudentUnit unit) {
        if(StudentUnit.GDANSK.equals(unit)) {
            return 5 * studentRepository.getMaxIndex();
        }
        else {
            return 10 * studentRepository.getMaxIndex();
        }
    }
}
