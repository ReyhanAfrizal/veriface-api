package com.reyhan.veriface.handler;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/15/2024 4:41 PM
@Last Modified 11/15/2024 4:41 PM
Version 1.0
*/


class ApiValidationError {
    private String field;
    private String message;
    private Object rejectedValue;
//    private String objectName;

    public ApiValidationError(String field, String message,Object rejectedValue) {
        this.field = field;
        this.message = message;
//        this.objectName = objectName;
        this.rejectedValue = rejectedValue;
    }


    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

