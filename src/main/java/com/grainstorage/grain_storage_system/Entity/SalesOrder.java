package com.grainstorage.grain_storage_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "grain_lot_id")
    private GrainLot grainLot;

    private Double quantityTons;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private SalesOrderStatus status;
}
