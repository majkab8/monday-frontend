package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

 //   private Long maxIndex = 5L;

    @BeforeAll
    static void setUpAll() {
        log.info("After all tests this setup is called");
    }

    @BeforeEach
    void setUp() {
        log.info("Before each test this setup is called");

    }

    @AfterEach
    void cleanUp() {
        log.info("After each test this cleanup is called");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("After all tests this cleanup is called");
    }

    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("18eb21a1-8e4b-4df2-936c-385405412559"), "Karola", StudentUnit.GDANSK, null);
       // maxIndex = 6L;
       // when(studentRepository.getMaxIndex()).thenReturn(5L);
        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(30, savedStudent.index());
        verify(studentRepository, times(1)).saveStudent(any());
    }

    @Test
    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("18eb21a1-8e4b-4df2-936c-385405412559"), "Karola", StudentUnit.WARSZAWA, null);
     //   maxIndex = 7L;
    //    when(studentRepository.getMaxIndex()).thenReturn(5L);

        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(50, savedStudent.index());
        verify(studentRepository, times(1)).saveStudent(any());
    }
}
