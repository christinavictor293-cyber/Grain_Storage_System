package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
