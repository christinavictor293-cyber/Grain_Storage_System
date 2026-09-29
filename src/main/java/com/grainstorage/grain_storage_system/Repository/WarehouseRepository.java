package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
