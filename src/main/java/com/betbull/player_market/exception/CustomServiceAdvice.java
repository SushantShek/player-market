package com.betbull.player_market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomServiceAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidInputRequestParamException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidRequestParamHandler(InvalidInputRequestParamException ex) {
        return ex.getMessage();
    }
}
