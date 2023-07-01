package com.vti.trangwebxemphimv2.service;

import com.vti.trangwebxemphimv2.entity.dto.AccountCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.AccountUpdateRequest;
import com.vti.trangwebxemphimv2.entity.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();

    Account getById(int id);

    Account create(AccountCreateRequest dto);

    Account update(AccountUpdateRequest dto);

    void deleteById(int id);

}
