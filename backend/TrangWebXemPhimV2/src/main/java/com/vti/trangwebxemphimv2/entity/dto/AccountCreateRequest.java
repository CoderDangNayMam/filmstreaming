package com.vti.trangwebxemphimv2.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountCreateRequest {

    private String username;

    private String password;

    private Date dateOfBirth;

    private String address;

    private String fullName;

    private String phoneNumber;

    private String email;
}
