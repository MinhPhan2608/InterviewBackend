package org.example.interviewbe.controllers.base;

import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected <T> ResponseEntity<ApiResponse<T>> success(T data){
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
