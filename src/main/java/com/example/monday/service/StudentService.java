package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentDataComponent;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import com.example.monday.exceptionHandler.InvalidStudentNameException;
import com.example.monday.exceptionHandler.RecordNotFoundException;
import com.example.monday.resource.CreateStudent;
import com.example.monday.resource.StudentDto;
import com.example.monday.resource.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

//@Log
@Service
@RequiredArgsConstructor
public class StudentService {

    //private static final String API_URL = "http://localhost:8080/students";

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

//    private final RestTemplate restTemplate = new RestTemplate();
//    private final WebClient webClient = WebClient.builder()
//            .baseUrl(API_URL)
//            .build();

    public Student saveStudent(CreateStudent createStudent) {
//        var toSave = studentMapper.toEntity(createStudent);
//        var index = createIndex(createStudent.getUnit());
//        toSave.setIndex(index);
//        studentRepository.save(toSave);
//        return toSave;
        return feignClient.saveStudents(createStudent);
    }

//        restTemplate.exchange(URI.create(API_URL), HttpMethod.POST,
//                new HttpEntity<>(createStudent), Void.class);

//        webClient.post()
//                .body(Mono.just(createStudent), CreateStudent.class)
//                .retrieve()
//                .toBodilessEntity()
//                .subscribe(entity -> log.info("Successfully saved student " + entity.getStatusCode()));
//
//    }

    public StudentDto getStudentById(UUID id){
//        return studentRepository.findById(id)
//                .map(studentMapper::toDto)
//                .orElseThrow(() -> new RecordNotFoundException("Student with id " + id + " not found"));
//        var responseEntity = restTemplate.getForObject(API_URL + "/" + id, StudentDto.class);
//        if(responseEntity.getStatusCode().is2xxSuccessful()){
//             return responseEntity.getBody();
//        } else if (responseEntity.getStatusCode().is4xxClientError()){
//            throw new InvalidStudentNameException("just to check error handling");
//        }
//        throw new RuntimeException();
        return feignClient.getStudentById(id);
    }

    public List<StudentDto> getStudentsByName(String name) {
        return studentRepository.getFromGdanskByName(name)
//                .uri(uriBuilder -> uriBuilder.queryParam("name", name).build())
//                .retrieve()
//                .bodyToFlux(StudentDto.class)
//                .toStream()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public void deleteByName(String name){
        var studentsByName = studentRepository.getAllByName(name);
        if(studentsByName.isEmpty()) {
            throw new InvalidStudentNameException("Student with name=" + name + " not exists.");
        }
        studentRepository.deleteAll(studentsByName);
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

    private final StudentsFeignClient feignClient;
}
