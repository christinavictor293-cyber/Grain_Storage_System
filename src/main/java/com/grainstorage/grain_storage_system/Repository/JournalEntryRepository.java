package com.grainstorage.grain_storage_system.Repository;

import com.grainstorage.grain_storage_system.Entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByJournalId(Long journalId);
}
