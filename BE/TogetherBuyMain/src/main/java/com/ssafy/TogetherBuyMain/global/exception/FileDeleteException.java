package com.ssafy.TogetherBuyMain.global.exception;

public class FileDeleteException extends RuntimeException {
    public FileDeleteException(String message) {
        super(message);
    }
    public FileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
