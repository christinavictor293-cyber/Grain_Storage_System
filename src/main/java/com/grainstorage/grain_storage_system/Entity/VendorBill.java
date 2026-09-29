package com.grainstorage.grain_storage_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vendor_bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grain_lot_id")
    private GrainLot grainLot;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Contact farmer;

    private Double amount;

    private LocalDate billDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status = BillStatus.PENDING;
}
