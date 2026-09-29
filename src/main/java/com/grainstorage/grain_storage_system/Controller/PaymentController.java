package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Entity.Payment;
import com.grainstorage.grain_storage_system.Service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/vendor-bill/{billId}")
    public Payment payVendorBill(@PathVariable Long billId, @RequestParam String mode) {
        return paymentService.payVendorBill(billId, mode);
    }
}
