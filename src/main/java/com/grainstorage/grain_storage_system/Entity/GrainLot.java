package com.grainstorage.grain_storage_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grain_lots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrainLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grainType;

    @Enumerated(EnumType.STRING)
    private GrainGrade grade;

    private Double quantityTons;

    private Double pricePerTon;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Contact farmer;
}
