package com.hocvui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
- Khi su ly global exception thi
vi du voi ResourceExistException
    try{
        if(true) throw new ResourceExistException();
    }catch(ResourceExistException e) {
        System.out.println(e.getMessage());
    }
+) chay vao catch , khong co catch moi chay vao class trong globalException

 */

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerException(){

    }


}
