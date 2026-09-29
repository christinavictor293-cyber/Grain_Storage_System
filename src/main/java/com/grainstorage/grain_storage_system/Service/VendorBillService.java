package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.*;
import com.grainstorage.grain_storage_system.Repository.GrainLotRepository;
import com.grainstorage.grain_storage_system.Repository.VendorBillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorBillService {

    private final VendorBillRepository vendorBillRepository;
    private final GrainLotRepository grainLotRepository;
    private final JournalService journalService;
    private final ChartOfAccountService chartOfAccountService;

    public VendorBillService(VendorBillRepository vendorBillRepository, GrainLotRepository grainLotRepository,
                             JournalService journalService, ChartOfAccountService chartOfAccountService) {
        this.vendorBillRepository = vendorBillRepository;
        this.grainLotRepository = grainLotRepository;
        this.journalService = journalService;
        this.chartOfAccountService = chartOfAccountService;
    }

    public VendorBill createBillFromGrainLot(Long grainLotId) {
        GrainLot lot = grainLotRepository.findById(grainLotId)
                .orElseThrow(() -> new RuntimeException("Grain lot not found with id: " + grainLotId));

        Double amount = lot.getQuantityTons() * lot.getPricePerTon();

        VendorBill bill = new VendorBill();
        bill.setGrainLot(lot);
        bill.setFarmer(lot.getFarmer());
        bill.setAmount(amount);
        bill.setBillDate(LocalDate.now());
        bill.setStatus(BillStatus.PENDING);
        bill = vendorBillRepository.save(bill);

        ChartOfAccount inventory = chartOfAccountService.getOrCreateAccount("Grain Stock Inventory", AccountType.ASSET);
        ChartOfAccount creditors = chartOfAccountService.getOrCreateAccount("Farmer Creditors", AccountType.LIABILITY);

        journalService.createBalancedJournal(
                "PURCHASE",
                "Vendor bill for grain lot #" + lot.getId(),
                inventory,
                creditors,
                amount
        );

        return bill;
    }

    public List<VendorBill> getAllVendorBills() {
        return vendorBillRepository.findAll();
    }
}