package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.AnalyticAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticAccountRepository extends JpaRepository<AnalyticAccount, Long> {
}
