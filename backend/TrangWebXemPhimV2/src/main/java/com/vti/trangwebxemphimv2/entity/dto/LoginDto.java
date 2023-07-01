package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.Role;
import lombok.Data;

@Data
public class LoginDto {
    private int id;
    private String username;
    private Role role;
    private String fullname;

}
