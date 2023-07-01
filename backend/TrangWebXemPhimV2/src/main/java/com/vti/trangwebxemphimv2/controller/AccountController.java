package com.vti.trangwebxemphimv2.controller;

import com.vti.trangwebxemphimv2.entity.dto.AccountCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.AccountUpdateRequest;
import com.vti.trangwebxemphimv2.entity.entity.Account;
import com.vti.trangwebxemphimv2.entity.entity.AccountStatus;
import com.vti.trangwebxemphimv2.entity.entity.ActiveAccount;
import com.vti.trangwebxemphimv2.repository.AccountRepository;
import com.vti.trangwebxemphimv2.repository.ActiveRepository;
import com.vti.trangwebxemphimv2.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@Validated
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ActiveRepository activeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/get-all")
    public List<Account> getAll(){
        return accountService.getAll();
    }
    @GetMapping("/{id}")
    public Account getById(@PathVariable int id){
        return accountService.getById(id);
    }
    @PostMapping("/create")
    public Account create(@RequestBody @Valid AccountCreateRequest dto){
        return accountService.create(dto);
    }
    @PutMapping("/update")
    public Account update(@RequestBody AccountUpdateRequest dto){
        return accountService.update(dto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String deleteById(@PathVariable int id){
        accountService.deleteById(id);
        return "Đã xoá thành công";
    }
    @GetMapping("/active/{uuid}")
    public String activeAccount(@PathVariable String uuid){
        Optional<ActiveAccount> optionalActiveAccount = activeRepository.findFirstByUuid(uuid);
        if (optionalActiveAccount.isEmpty()){
            return "Mã kích hoạt không tồn tại.";
        }

        ActiveAccount activeAccount = optionalActiveAccount.get();
        Account account = activeAccount.getAccount();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return "Tài khoản của bạn đã được kích hoạt";
    }

}
