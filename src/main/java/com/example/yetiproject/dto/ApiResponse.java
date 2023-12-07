package com.example.yetiproject.dto;

import com.example.yetiproject.exception.ErrorMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private String message;
    private T data;


    private ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // GET, POST, PUT
    public static <T> ApiResponse<T> success(String message, T data ) {
        return new ApiResponse<>(SUCCESS_STATUS, message, data);
    }

    // DELETE
    public static ApiResponse<?> successWithNoContent(String message) {
        return new ApiResponse<>(SUCCESS_STATUS, message, null);
    }
}
