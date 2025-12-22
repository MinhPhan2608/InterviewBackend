package org.example.interviewbe.controllers.base;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BaseResponse<T> {
    boolean success;
    int code;
    String message;
    T data;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("OK")
                .data(data)
                .build();
    }

    public static BaseResponse<?> error(int code, String message) {
        return BaseResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }
}

