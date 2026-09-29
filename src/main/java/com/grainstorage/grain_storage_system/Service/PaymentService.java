package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.*;
import com.grainstorage.grain_storage_system.Repository.PaymentRepository;
import com.grainstorage.grain_storage_system.Repository.VendorBillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final VendorBillRepository vendorBillRepository;
    private final JournalService journalService;
    private final ChartOfAccountService chartOfAccountService;

    public PaymentService(PaymentRepository paymentRepository, VendorBillRepository vendorBillRepository,
                          JournalService journalService, ChartOfAccountService chartOfAccountService) {
        this.paymentRepository = paymentRepository;
        this.vendorBillRepository = vendorBillRepository;
        this.journalService = journalService;
        this.chartOfAccountService = chartOfAccountService;
    }

    public Payment payVendorBill(Long billId, String mode) {
        VendorBill bill = vendorBillRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Vendor bill not found with id: " + billId));

        if (bill.getStatus() == BillStatus.PAID) {
            throw new IllegalStateException("This vendor bill has already been paid.");
        }

        Payment payment = new Payment();
        payment.setVendorBill(bill);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmountPaid(bill.getAmount());
        payment.setPaymentMode(mode);
        payment = paymentRepository.save(payment);

        bill.setStatus(BillStatus.PAID);
        vendorBillRepository.save(bill);

        ChartOfAccount creditors = chartOfAccountService.getOrCreateAccount("Farmer Creditors", AccountType.LIABILITY);
        ChartOfAccount bank = chartOfAccountService.getOrCreateAccount("Cash", AccountType.ASSET);

        journalService.createBalancedJournal(
                "BANK",
                "Payment for vendor bill #" + bill.getId(),
                creditors,
                bank,
                bill.getAmount()
        );

        return payment;
    }
}
