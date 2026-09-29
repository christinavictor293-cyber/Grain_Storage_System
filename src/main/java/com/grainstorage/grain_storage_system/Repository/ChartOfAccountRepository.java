package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.ChartOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChartOfAccountRepository extends JpaRepository<ChartOfAccount, Long> {
    Optional<ChartOfAccount> findByAccountName(String accountName);
}
