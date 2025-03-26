package com.example.coordinator;

import java.util.List;
import java.util.Map;

public class TransactionRequest {
    private List<String> participants;
    private String transactionId;

    private Map<String, Object> payload;

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
