/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tri.erp.spring.advice;

import javax.servlet.http.HttpServletRequest;

import com.tri.erp.spring.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author TSI Admin
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) { 
        return "index";
    } 
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleIOException(){ 
        return "404";
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String accessDeniedException(){ 
        return "403";
    }
}
