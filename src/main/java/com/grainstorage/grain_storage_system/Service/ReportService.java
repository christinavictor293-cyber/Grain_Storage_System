package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.AccountType;
import com.grainstorage.grain_storage_system.Entity.ChartOfAccount;
import com.grainstorage.grain_storage_system.Repository.BudgetRepository;
import com.grainstorage.grain_storage_system.Repository.ChartOfAccountRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ChartOfAccountRepository chartOfAccountRepository;
    private final BudgetRepository budgetRepository;

    public ReportService(ChartOfAccountRepository chartOfAccountRepository, BudgetRepository budgetRepository) {
        this.chartOfAccountRepository = chartOfAccountRepository;
        this.budgetRepository = budgetRepository;
    }

    public Map<String, Object> getBalanceSheet() {
        List<ChartOfAccount> accounts = chartOfAccountRepository.findAll();

        Map<String, Object> result = new HashMap<>();
        for (AccountType type : AccountType.values()) {
            Map<String, Double> group = accounts.stream()
                    .filter(a -> a.getAccountType() == type)
                    .collect(Collectors.toMap(ChartOfAccount::getAccountName, ChartOfAccount::getBalance));
            result.put(type.name().toLowerCase(), group);
        }
        return result;
    }

    public Map<String, Double> getProfitAndLoss() {
        List<ChartOfAccount> accounts = chartOfAccountRepository.findAll();

        double totalIncome = accounts.stream()
                .filter(a -> a.getAccountType() == AccountType.INCOME)
                .mapToDouble(ChartOfAccount::getBalance)
                .sum();

        double totalExpense = accounts.stream()
                .filter(a -> a.getAccountType() == AccountType.EXPENSE)
                .mapToDouble(ChartOfAccount::getBalance)
                .sum();

        Map<String, Double> result = new HashMap<>();
        result.put("totalIncome", totalIncome);
        result.put("totalExpense", totalExpense);
        result.put("netProfit", totalIncome - totalExpense);
        return result;
    }

    public List<Map<String, Object>> getBudgetVariance() {
        return budgetRepository.findAll().stream().map(b -> {
            Map<String, Object> row = new HashMap<>();
            row.put("budgetId", b.getId());
            row.put("analyticAccount", b.getAnalyticAccount().getName());
            row.put("plannedAmount", b.getPlannedAmount());
            row.put("actualAmount", b.getActualAmount());
            row.put("variance", b.getPlannedAmount() - b.getActualAmount());
            return row;
        }).collect(Collectors.toList());
    }
}
