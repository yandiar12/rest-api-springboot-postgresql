/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.exception;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author YAR
 */

public class ApiError {
    private int status;
    private HttpStatus errorStatus;
    private String message;
    private List<String> errors;
 
    public ApiError(int status, HttpStatus errorStatus, String message, List<String> errors) {
        super();
        this.status = status;
        this.errorStatus = errorStatus;
        this.message = message;
        this.errors = errors;
    }
 
    public ApiError(int status, HttpStatus errorStatus, String message, String errorDetail) {
        super();
        this.status = status;
        this.errorStatus = errorStatus;
        this.message = message;
        errors = Arrays.asList(errorDetail);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(HttpStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    
}
