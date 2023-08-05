package com.example.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthExceptionHandle extends AuthenticationException {


    public AuthExceptionHandle(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthExceptionHandle(String msg) {
        super(msg);
    }
}
