package com.kotakbank.assignment.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DOWN_STREAM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KTB-DOWN-STREAM-001", "The dowmstream syatem returns error."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KTB-SERVERERROR-002", "Something went wrong.");

    private HttpStatus status;
    private String errorCode;
    private String detail;

}
