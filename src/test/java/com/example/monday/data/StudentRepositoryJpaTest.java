package com.example.monday.data;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class StudentRepositoryJpaTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp(){
        var student1 = new Student("Maja", StudentUnit.GDANSK, 13L);
        var student2 = new Student("Aga", StudentUnit.WARSZAWA, 20L);
        studentRepository.save(student1);
        studentRepository.save(student2);
    }

    @Test
    void givenStudents_whenGetMaxIndex_ThenReturnValidResult() {
        var maxIndex = studentRepository.getMaxIndex();
        assertTrue(maxIndex.isPresent());
        assertEquals(20L, maxIndex.get());
    }
}
