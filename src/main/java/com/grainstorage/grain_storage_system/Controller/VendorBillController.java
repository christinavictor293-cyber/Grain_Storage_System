package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Entity.VendorBill;
import com.grainstorage.grain_storage_system.Service.VendorBillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bills")
public class VendorBillController {

    private final VendorBillService vendorBillService;

    public VendorBillController(VendorBillService vendorBillService) {
        this.vendorBillService = vendorBillService;
    }

    @PostMapping("/from-grain-lot/{grainLotId}")
    public VendorBill createBillFromGrainLot(@PathVariable Long grainLotId) {
        return vendorBillService.createBillFromGrainLot(grainLotId);
    }

    @GetMapping
    public List<VendorBill> getAllVendorBills() {
        return vendorBillService.getAllVendorBills();
    }
}
