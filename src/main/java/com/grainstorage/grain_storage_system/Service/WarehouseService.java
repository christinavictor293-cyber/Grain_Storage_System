package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.Warehouse;
import com.grainstorage.grain_storage_system.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
    }

    public Double getRemainingCapacity(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        return warehouse.getCapacityTons() - warehouse.getCurrentStockTons();
    }
}