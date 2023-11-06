package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentDataComponent;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import com.example.monday.exceptionHandler.RecordNotFoundException;
import com.example.monday.resource.CreateStudent;
import com.example.monday.resource.StudentDto;
import com.example.monday.resource.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public Student saveStudent(CreateStudent createStudent) {
        var toSave = studentMapper.toEntity(createStudent);
        var index = createIndex(createStudent.unit());
        toSave.setIndex(index);
        studentRepository.save(toSave);
        return toSave;
    }

    public StudentDto getStudentById(UUID id){
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException("Student with id " + id + " not found"));
    }

    public void deleteByName(String name){
        studentRepository.deleteByName(name);
    }

    private Long createIndex(StudentUnit unit) {
        long maxIndex = studentRepository.getMaxIndex().orElse(0L);
        if(StudentUnit.GDANSK.equals(unit)) {
            return 5 * maxIndex;
        }
        else {
            return 10 * maxIndex;
        }
    }
}
