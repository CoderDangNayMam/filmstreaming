package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
public class AccountUpdateRequest {
    private int id;

    private String username;

    private String password;

    private Date dateOfBirth;

    private String address;

    private String fullName;

    private String phoneNumber;

    private String email;

}
