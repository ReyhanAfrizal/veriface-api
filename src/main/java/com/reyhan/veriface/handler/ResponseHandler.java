package com.reyhan.veriface.handler;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/15/2024 4:45 PM
@Last Modified 11/15/2024 4:45 PM
Version 1.0
*/


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public ResponseEntity<Object> generateResponse(String message,
                                                   HttpStatus status,
                                                   Object responseObj,
                                                   Object errorCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data",responseObj==null?"":responseObj);
//        map.put("timestamp", new Date());
        map.put("success",!status.isError());
        if(errorCode != null)
        {
            map.put("error_code",errorCode);
//            map.put("path",request.getPathInfo());
        }
        return new ResponseEntity<Object>(map,status);
    }

    public Map<String,Object> generateResponseRaw(String message,
                                                  HttpStatus status,
                                                  Object responseObj,
                                                  Object errorCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj==null?"":responseObj);
//        map.put("timestamp", new Date());
        map.put("success",!status.isError());
        if(errorCode != null)
        {
            map.put("errorCode",errorCode);
//            map.put("path",request.getPathInfo());
        }
        map.put("httpStatus",status);
        return map;
    }
}
