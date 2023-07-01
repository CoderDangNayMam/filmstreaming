package com.vti.trangwebxemphimv2.entity.dto;

import lombok.Data;

@Data
public class BaseRequest {
    protected int page;
    protected int size;
    protected String sortField;
    protected String sortType; // asc, desc

}
