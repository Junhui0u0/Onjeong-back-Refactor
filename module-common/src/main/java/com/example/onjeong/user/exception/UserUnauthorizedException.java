package com.example.onjeong.user.exception;

import com.example.onjeong.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserUnauthorizedException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserUnauthorizedException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
