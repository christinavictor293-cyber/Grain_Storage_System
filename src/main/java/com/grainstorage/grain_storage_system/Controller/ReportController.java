package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/balance-sheet")
    public Map<String, Object> getBalanceSheet() {
        return reportService.getBalanceSheet();
    }

    @GetMapping("/profit-and-loss")
    public Map<String, Double> getProfitAndLoss() {
        return reportService.getProfitAndLoss();
    }

    @GetMapping("/budget-variance")
    public List<Map<String, Object>> getBudgetVariance() {
        return reportService.getBudgetVariance();
    }
}
