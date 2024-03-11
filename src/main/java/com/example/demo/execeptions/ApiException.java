package com.example.demo.execeptions;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
    public ApiException(){
        super();
    }
}
