package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorBillRepository extends JpaRepository<VendorBill, Long> {
}
