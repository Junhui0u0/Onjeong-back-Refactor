package com.example.onjeong.home.exception;

import com.example.onjeong.error.ErrorCode;
import lombok.Getter;

@Getter
public class RandCoinDuplicateException extends RuntimeException{

    private final ErrorCode errorCode;

    public RandCoinDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}