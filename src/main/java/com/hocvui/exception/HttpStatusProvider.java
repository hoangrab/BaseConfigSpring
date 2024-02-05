package com.hocvui.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusProvider {
    HttpStatus getStatus();
}
