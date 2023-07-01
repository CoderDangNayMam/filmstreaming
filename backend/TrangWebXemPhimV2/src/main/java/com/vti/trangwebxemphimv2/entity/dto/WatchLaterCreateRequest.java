package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.Account;
import lombok.Data;

@Data
public class WatchLaterCreateRequest {
    private int accountId;
    private int filmId;
}
