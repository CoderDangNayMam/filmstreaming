package com.vti.trangwebxemphimv2.config.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class AppException extends RuntimeException {
    private Instant timestamp; //thời gian xảy ra lỗi
    private int status; // mã lỗi, trạng thái
    private String message; // nguyên nhân lỗi
    private String path; // api xảy ra lỗi

    public AppException(int status, String errorMessage, String path){
        this.timestamp = Instant.now();
        this.status = status;
        this.message = errorMessage;
        this.path = path;
    }
    public AppException(ErrorResponseEnum responseEnum){
        this.timestamp = Instant.now();
        this.status = responseEnum.status;
        this.message = responseEnum.message;
    }
}
