package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
