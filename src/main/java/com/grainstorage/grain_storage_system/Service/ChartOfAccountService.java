package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.AccountType;
import com.grainstorage.grain_storage_system.Entity.ChartOfAccount;
import com.grainstorage.grain_storage_system.Repository.ChartOfAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class ChartOfAccountService {

    private final ChartOfAccountRepository chartOfAccountRepository;

    public ChartOfAccountService(ChartOfAccountRepository chartOfAccountRepository) {
        this.chartOfAccountRepository = chartOfAccountRepository;
    }

    public ChartOfAccount getOrCreateAccount(String name, AccountType type) {
        return chartOfAccountRepository.findByAccountName(name)
                .orElseGet(() -> {
                    ChartOfAccount account = new ChartOfAccount();
                    account.setAccountName(name);
                    account.setAccountType(type);
                    account.setBalance(0.0);
                    return chartOfAccountRepository.save(account);
                });
    }
}