package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Entity.AnalyticAccount;
import com.grainstorage.grain_storage_system.Entity.Budget;
import com.grainstorage.grain_storage_system.Service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.createBudget(budget);
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @GetMapping("/{id}/variance")
    public Double getVariance(@PathVariable Long id) {
        return budgetService.computeVariance(id);
    }

    @PostMapping("/analytic-accounts")
    public AnalyticAccount createAnalyticAccount(@RequestBody AnalyticAccount analyticAccount) {
        return budgetService.createAnalyticAccount(analyticAccount);
    }

    @GetMapping("/analytic-accounts")
    public List<AnalyticAccount> getAllAnalyticAccounts() {
        return budgetService.getAllAnalyticAccounts();
    }
}
