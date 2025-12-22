package org.example.interviewbe.controllers.base;

import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected <T> ResponseEntity<BaseResponse<T>> success(T data){
        return ResponseEntity.ok(BaseResponse.success(data));
    }
}
