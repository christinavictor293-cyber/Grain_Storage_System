package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.ChartOfAccount;
import com.grainstorage.grain_storage_system.Entity.Journal;
import com.grainstorage.grain_storage_system.Entity.JournalEntry;
import com.grainstorage.grain_storage_system.Repository.ChartOfAccountRepository;
import com.grainstorage.grain_storage_system.Repository.JournalEntryRepository;
import com.grainstorage.grain_storage_system.Repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final ChartOfAccountRepository chartOfAccountRepository;

    public JournalService(JournalRepository journalRepository, JournalEntryRepository journalEntryRepository,
                          ChartOfAccountRepository chartOfAccountRepository) {
        this.journalRepository = journalRepository;
        this.journalEntryRepository = journalEntryRepository;
        this.chartOfAccountRepository = chartOfAccountRepository;
    }

    public Journal createBalancedJournal(String type, String description,
                                          ChartOfAccount debitAccount, ChartOfAccount creditAccount,
                                          Double amount) {

        Journal journal = new Journal();
        journal.setJournalType(type);
        journal.setDescription(description);
        journal.setJournalDate(LocalDate.now());
        journal = journalRepository.save(journal);

        JournalEntry debitEntry = new JournalEntry();
        debitEntry.setJournal(journal);
        debitEntry.setAccount(debitAccount);
        debitEntry.setDebitAmount(amount);
        debitEntry.setCreditAmount(0.0);
        journalEntryRepository.save(debitEntry);

        JournalEntry creditEntry = new JournalEntry();
        creditEntry.setJournal(journal);
        creditEntry.setAccount(creditAccount);
        creditEntry.setDebitAmount(0.0);
        creditEntry.setCreditAmount(amount);
        journalEntryRepository.save(creditEntry);

        // Update running balances on the accounts
        debitAccount.setBalance(debitAccount.getBalance() + amount);
        creditAccount.setBalance(creditAccount.getBalance() + amount);
        chartOfAccountRepository.save(debitAccount);
        chartOfAccountRepository.save(creditAccount);

        return journal;
    }
}