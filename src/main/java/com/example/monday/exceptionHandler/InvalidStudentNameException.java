package com.example.monday.exceptionHandler;

public class InvalidStudentNameException extends RuntimeException{
    public InvalidStudentNameException(String s){
        super(s);
    }
}
