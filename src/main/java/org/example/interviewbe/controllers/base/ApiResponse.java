package org.example.interviewbe.controllers.base;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ApiResponse<T> {
    boolean success;
    int code;
    String message;
    T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("OK")
                .data(data)
                .build();
    }

    public static ApiResponse<?> error(int code, String message) {
        return ApiResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }
}

