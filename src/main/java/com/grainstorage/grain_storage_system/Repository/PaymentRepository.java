package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}