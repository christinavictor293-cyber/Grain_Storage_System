package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.GrainLot;
import com.grainstorage.grain_storage_system.Entity.Warehouse;
import com.grainstorage.grain_storage_system.Repository.GrainLotRepository;
import com.grainstorage.grain_storage_system.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrainLotService {

    private final GrainLotRepository grainLotRepository;
    private final WarehouseRepository warehouseRepository;

    public GrainLotService(GrainLotRepository grainLotRepository, WarehouseRepository warehouseRepository) {
        this.grainLotRepository = grainLotRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public GrainLot addGrainLot(GrainLot lot) {
        Warehouse warehouse = warehouseRepository.findById(lot.getWarehouse().getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        double newTotal = warehouse.getCurrentStockTons() + lot.getQuantityTons();

        if (newTotal > warehouse.getCapacityTons()) {
            throw new IllegalStateException(
                "Cannot add grain lot: warehouse capacity exceeded. " +
                "Capacity: " + warehouse.getCapacityTons() +
                ", Current stock: " + warehouse.getCurrentStockTons() +
                ", Attempted addition: " + lot.getQuantityTons()
            );
        }

        warehouse.setCurrentStockTons(newTotal);
        warehouseRepository.save(warehouse);

        lot.setWarehouse(warehouse);
        return grainLotRepository.save(lot);
    }

    public List<GrainLot> getAllGrainLots() {
        return grainLotRepository.findAll();
    }

    public List<GrainLot> getGrainLotsByWarehouse(Long warehouseId) {
        return grainLotRepository.findByWarehouseId(warehouseId);
    }
}
