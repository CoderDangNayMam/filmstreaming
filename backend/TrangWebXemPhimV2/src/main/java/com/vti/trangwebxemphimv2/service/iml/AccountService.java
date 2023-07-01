package com.vti.trangwebxemphimv2.service.iml;

import com.vti.trangwebxemphimv2.config.exception.AppException;
import com.vti.trangwebxemphimv2.config.exception.ErrorResponseEnum;
import com.vti.trangwebxemphimv2.entity.dto.AccountCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.AccountUpdateRequest;
import com.vti.trangwebxemphimv2.entity.entity.Account;
import com.vti.trangwebxemphimv2.entity.entity.AccountStatus;
import com.vti.trangwebxemphimv2.entity.entity.ActiveAccount;
import com.vti.trangwebxemphimv2.entity.entity.Role;
import com.vti.trangwebxemphimv2.repository.AccountRepository;
import com.vti.trangwebxemphimv2.repository.ActiveRepository;
import com.vti.trangwebxemphimv2.service.IAccountService;
import com.vti.trangwebxemphimv2.service.IMailSenderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService, UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected IMailSenderService mailSenderService;

    @Autowired
    private ActiveRepository activeRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        } else {
            return optionalAccount.get();
        }
    }

    @Override
    public Account create(AccountCreateRequest dto) {
        Account account = new Account();
        BeanUtils.copyProperties(dto,account);

        String pwEnconder = passwordEncoder.encode(dto.getPassword());
        account.setPassword(pwEnconder);
        account.setRole(Role.USER);
        account.setAccountStatus(AccountStatus.PENDING);

        account = accountRepository.save(account);

        ActiveAccount activeAccount = new ActiveAccount();
        String uuid = UUID.randomUUID().toString();
        activeAccount.setUuid(uuid);
        activeAccount.setAccount(account);
        activeRepository.save(activeAccount);



        String text = "Hello" + dto.getUsername();

        mailSenderService.sendMessageWithHTML(dto.getEmail(), "Kích hoạt tài khoản", text );

        return account;
    }

    @Override
    public Account update(AccountUpdateRequest dto) {
        Account account = getById(dto.getId());
        if (account != null){
            BeanUtils.copyProperties(dto,account);
            return accountRepository.save(account);
        } else {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
    }

    @Override
    public void deleteById(int id) {
        Account account = getById(id);
        if (account != null){
            accountRepository.deleteById(id);
        } else {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isEmpty()){
            throw new UsernameNotFoundException("Username không tồn tại");
        }
        // nếu account có tồn tại thì tạo ra đối tượng UserDetails từ Acocunt
        Account account = optionalAccount.get();
        /**
         * account.getUsername(): username
         * account.getPassword(): password đã được mã hoá.
         * Collections.emptyList(): list quyền của user
         */
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(account.getRole());
        return new User(username, account.getPassword(),authorityList);
    }
}
