package com.betbull.player_market.exception;

public class InvalidInputRequestParamException extends RuntimeException{
    public InvalidInputRequestParamException(String str) {
        super("Request parameter " + str);
    }
}
