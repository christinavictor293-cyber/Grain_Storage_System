package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {
}
