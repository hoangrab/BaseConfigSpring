package com.hocvui.exception;

import org.springframework.http.HttpStatus;

public class ResourceExistException extends AppException implements HttpStatusProvider {
    private HttpStatus httpStatus;
    public ResourceExistException(String resource,HttpStatus httpStatus) {
        super(String.format("Resource %s not found",resource));
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }
}
