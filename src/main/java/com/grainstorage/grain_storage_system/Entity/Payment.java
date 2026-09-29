package com.grainstorage.grain_storage_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_bill_id")
    private VendorBill vendorBill;

    @ManyToOne
    @JoinColumn(name = "customer_invoice_id")
    private CustomerInvoice customerInvoice;

    private LocalDate paymentDate;

    private Double amountPaid;

    private String paymentMode; // BANK or CASH
}