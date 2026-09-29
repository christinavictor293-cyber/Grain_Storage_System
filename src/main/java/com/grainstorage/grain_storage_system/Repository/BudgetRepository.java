package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
