package com.example.demo.config;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ErrorHandlerController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseBody
    public ResponseEntity<String> handleBusinessException(HttpServletRequest request, DateTimeParseException ex) {
        return new ResponseEntity<String>("Bad date  format.", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseBody
    public ResponseEntity<String> handleBusinessException(HttpServletRequest request, PropertyReferenceException ex) {
        return new ResponseEntity<String>("Bad sort parameter.", HttpStatus.BAD_REQUEST);
    }


}
