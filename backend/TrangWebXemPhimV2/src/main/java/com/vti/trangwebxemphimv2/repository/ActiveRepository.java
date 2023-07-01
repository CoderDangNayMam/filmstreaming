package com.vti.trangwebxemphimv2.repository;

import com.vti.trangwebxemphimv2.entity.entity.ActiveAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveRepository extends JpaRepository<ActiveAccount, Integer> {
    Optional<ActiveAccount> findFirstByUuid(String uuid);
}
