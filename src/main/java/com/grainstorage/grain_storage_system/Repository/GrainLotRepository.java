package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.GrainLot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrainLotRepository extends JpaRepository<GrainLot, Long> {
    List<GrainLot> findByWarehouseId(Long warehouseId);
}
