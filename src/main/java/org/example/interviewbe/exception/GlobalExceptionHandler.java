package org.example.interviewbe.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.controllers.base.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), String.join(", ", errors)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), String.join(", ", errors)));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<BaseResponse<?>> noResourceFound(NoResourceFoundException ex){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        BaseResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage())
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> methodNotAllowed(HttpRequestMethodNotSupportedException ex){
        return  ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        BaseResponse.error(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage())
                );
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BaseResponse<?>> jwtException(JwtException ex){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        BaseResponse.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> internalServerError(Exception ex){
        log.error("Internal server error: {}", ex.getMessage());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Go check the logs ahhh")
                );
    }
}
