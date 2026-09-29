package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {
}
