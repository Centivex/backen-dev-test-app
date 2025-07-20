package com.example.backend_dev_test_app.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String menssage){
        super(menssage);
    }
}
