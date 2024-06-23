package com.poglibrary.backend.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneralExceptions(Exception e) {
        Map<String, Object> map = new HashMap<>();
        List<Object> errorList = new ArrayList<>();
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("entity",e.toString());
        errorMap.put("errorCode","general.error");
        errorMap.put("errorMessage",e.getMessage());
        errorMap.put("stackTrace",e.getStackTrace());
        errorList.add(errorMap);


        map.put("errorCount", 1);
        map.put("errorList", errorList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
    }
}
