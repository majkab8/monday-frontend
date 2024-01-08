package com.example.monday;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import com.example.monday.resource.CreateStudent;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.RestAssured.when;
import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentResourceRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void setUp(){
        mockMvc(mockMvc);
    }

    @Test
    void givenStudentInDbWhenGetByIdThenReturnStudentDto() {
        var student = repository.save(new Student("Maja", StudentUnit.GDANSK, 15L));

        when()
                .get("/students/" + student.getId())
                .then()
                .status(HttpStatus.OK)
                .body("id", equalTo(student.getId().toString()))
                .body("name", equalTo(student.getUnit().toString()))
                .body("unit", equalTo(student.getUnit().toString()))
                .body("index", equalTo(student.getIndex().intValue()));
    }

    @Test
    void givenStudentDataWhenCreateStudentThenResponseIsCreated(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateStudent("Karola", StudentUnit.GDANSK))
                .when()
                .post("/students")
                .then()
                .status(HttpStatus.CREATED);
    }

    @Test
    void givenStudentsInDbWhenGetByNameThenReturnList() {
        var student = repository.save(new Student("Karola", StudentUnit.GDANSK));

        given()
                .param("name", "Karola")
                .when()
                .get("/students")
                .then()
                .body("$.size()", equalTo(1))
                .body("[0].id", equalTo(student.getId().toString()));
    }
}
