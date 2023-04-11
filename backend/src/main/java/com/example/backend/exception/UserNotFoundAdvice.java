package com.example.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Map;
import java.util.HashMap;
@ControllerAdvice
public class UserNotFoundAdvice {
    /**
     * Exception handler method for UserNotFoundExceptions.
     * Returns a JSON error message with a 404 status code.
     *
     * @param exception the UserNotFoundException to handle
     * @return a HashMap with an "errorMessage" key and the exception message as its value
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> exceptionHandler(UserNotFoundException exception){
        Map<String, String> errorMap=new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());

        return errorMap;
    }
}
