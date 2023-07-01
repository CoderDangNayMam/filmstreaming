package com.vti.trangwebxemphimv2.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppExceptionDto {
    private Instant timestamp; //thời gian xảy ra lỗi
    private int status; // mã lỗi, trạng thái
    private String message; // nguyên nhân lỗi
    private String path; // api xảy ra lỗi

    public AppExceptionDto(int status, String errorMessage, String path){
        this.timestamp = Instant.now();
        this.status = status;
        this.message = errorMessage;
        this.path = path;
    }
    public AppExceptionDto(ErrorResponseEnum responseEnum){
        this.timestamp = Instant.now();
        this.status = responseEnum.status;
        this.message = responseEnum.message;
    }
}
