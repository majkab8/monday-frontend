package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentDataComponent;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import com.example.monday.resource.CreateStudent;
import com.example.monday.resource.StudentMapper;
import com.example.monday.resource.StudentResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockStudentServiceTest {

    //Mock tworzy nam proxy naszej klasy - to sprawia, że wywołania tej klasy nie wykonają rzeczywistej metody
    //i każdorazowe jej wywołanie musimy skonfigurować, możemy też tak jak w przypadku Spy śledzić jej wywołania

    private StudentRepository studentRepository = mock(StudentRepository.class);

    private StudentMapper studentMapper = new StudentMapper();

    //InjectMocks pozwala nam stworzyć klasę testowaną z wykorzystaniem obiektów, które zdefiniowaliśmy
    //jako elementy mockito używając adnotacji Mock/Spy
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository, studentMapper);
    }


    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new CreateStudent("Karola", StudentUnit.GDANSK);
        /**poniżej przykład konfigurowania zachowania mocka przy wywołaniu konkrentej metody
         */
        when(studentRepository.getMaxIndex()).thenReturn(Optional.of(5L));

        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.getName(), savedStudent.getName());
        assertEquals(student.getUnit(), savedStudent.getUnit());
        assertEquals(10, savedStudent.getIndex());
        verify(studentRepository, times(1)).save(any());
    }


    @Test
    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new CreateStudent("Karola", StudentUnit.WARSZAWA);
        /** poniżej przykład konfigurowania zachowania mocka przy wywołaniu konkrentej metody
         zachowanie analogiczne jak powyżej, inny zapis
         */
        doReturn(Optional.of(7L)).when(studentRepository).getMaxIndex();

        //when
        studentService.saveStudent(student);

        //then
        /** ArgumentCaptor pozwala nam odczytać wartość parametru przekazanego do metody wywołanej w ramach mocka
         */
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).save(argumentCaptor.capture());
        var savedStudent = argumentCaptor.getValue();
        assertEquals(student.getUnit(), savedStudent.getName());
        assertEquals(student.getUnit(), savedStudent.getUnit());
        assertEquals(70, savedStudent.getIndex());
    }
}
//@Log
//@ExtendWith(MockitoExtension.class)
//class StudentServiceTest {
//
//
//    @Spy
//    private StudentDataComponent studentRepository;
//
//    @InjectMocks
//    private StudentService studentService;

 //   private Long maxIndex = 5L;

//    @BeforeAll
//    static void setUpAll() {
//        log.info("After all tests this setup is called");
//    }
//
//    @BeforeEach
//    void setUp() {
//        log.info("Before each test this setup is called");
//
//    }
//
//    @AfterEach
//    void cleanUp() {
//        log.info("After each test this cleanup is called");
//    }
//
//    @AfterAll
//    static void cleanUpAll() {
//        log.info("After all tests this cleanup is called");
//    }

//    @Test
//    void givenGdanskUnitWhenSaveStudentThenGetValidIndex() {
//        //given
//        var student = new Student(UUID.fromString("18eb21a1-8e4b-4df2-936c-385405412559"), "Karola", StudentUnit.GDANSK, null);
//       // maxIndex = 6L;
//       // when(studentRepository.getMaxIndex()).thenReturn(5L);
//        //when
//        var savedStudent = studentService.saveStudent(student);
//
//        //then
//        assertEquals(student.getId(), savedStudent.getId());
//        assertEquals(student.getName(), savedStudent.getName());
//        assertEquals(student.getUnit(), savedStudent.getUnit());
//        assertEquals(30, savedStudent.getIndex());
//        verify(studentRepository, times(1)).saveStudent(any());
//    }

//    @Test
//    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex() {
//        //given
//        var student = new Student(UUID.fromString("18eb21a1-8e4b-4df2-936c-385405412559"), "Karola", StudentUnit.WARSZAWA, null);
//     //   maxIndex = 7L;
//    //    when(studentRepository.getMaxIndex()).thenReturn(5L);
//
//        //when
//        var savedStudent = studentService.saveStudent(student);
//
//        //then
//        assertEquals(student.getId(), savedStudent.getId());
//        assertEquals(student.getName(), savedStudent.getName());
//        assertEquals(student.getUnit(), savedStudent.getUnit());
//        assertEquals(50, savedStudent.getIndex());
//        verify(studentRepository, times(1)).saveStudent(any());
//    }
//}
