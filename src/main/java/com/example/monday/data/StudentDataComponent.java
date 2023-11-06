package com.example.monday.data;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class StudentDataComponent {

    private final List<Student> students = new ArrayList<>();

    public void saveStudent(Student student) {
        students.add(student);
    }

    public Student getStudentById(UUID id){
        return students.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Long getMaxIndex() {
        return students.stream()
                .map(Student::getIndex)
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }
}
