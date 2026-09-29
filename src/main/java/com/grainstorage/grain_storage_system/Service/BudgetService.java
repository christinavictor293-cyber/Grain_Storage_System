package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.AnalyticAccount;
import com.grainstorage.grain_storage_system.Entity.Budget;
import com.grainstorage.grain_storage_system.Repository.AnalyticAccountRepository;
import com.grainstorage.grain_storage_system.Repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final AnalyticAccountRepository analyticAccountRepository;

    public BudgetService(BudgetRepository budgetRepository, AnalyticAccountRepository analyticAccountRepository) {
        this.budgetRepository = budgetRepository;
        this.analyticAccountRepository = analyticAccountRepository;
    }

    public Budget createBudget(Budget budget) {
        budget.setActualAmount(0.0);
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }

    public double computeVariance(Long budgetId) {
        Budget budget = getBudgetById(budgetId);
        return budget.getPlannedAmount() - budget.getActualAmount();
    }

    public void incrementActualAmount(Long budgetId, Double amount) {
        Budget budget = getBudgetById(budgetId);
        budget.setActualAmount(budget.getActualAmount() + amount);
        budgetRepository.save(budget);
    }

    public AnalyticAccount createAnalyticAccount(AnalyticAccount analyticAccount) {
        return analyticAccountRepository.save(analyticAccount);
    }

    public List<AnalyticAccount> getAllAnalyticAccounts() {
        return analyticAccountRepository.findAll();
    }
}
