package com.hocvui.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException implements HttpStatusProvider{

    private HttpStatus httpStatus;
    public ResourceNotFoundException(String resource, HttpStatus httpStatus) {
        super(String.format("Resource %s already exists",resource));
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }
}
