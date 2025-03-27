package com.example.participant2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean prepare(TransactionRequest request) {
        // Preparation logic (e.g., checking resource availability)
        try {
            jdbcTemplate.update("INSERT INTO transaction_log (transaction_id, status) VALUES (?, ?)", request.getTransactionId(), "PREPARED");
            System.out.println("Transaction prepared for request: " + request.getTransactionId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void commit(TransactionRequest request) {
        // Simulate commit logic (e.g., persisting changes to the database)
        jdbcTemplate.update("UPDATE transaction_log SET status = ? WHERE transaction_id = ?", "COMMITTED", request.getTransactionId());
        System.out.println("Transaction committed for request: " + request.getTransactionId());
    }

    public void rollback(TransactionRequest request) {
        // Simulate rollback logic (e.g., reverting changes)
        jdbcTemplate.update("UPDATE transaction_log SET status = ? WHERE transaction_id = ?", "ROLLED_BACK", request.getTransactionId());
        System.out.println("Transaction rolled back for request: " + request.getTransactionId());
    }
}
