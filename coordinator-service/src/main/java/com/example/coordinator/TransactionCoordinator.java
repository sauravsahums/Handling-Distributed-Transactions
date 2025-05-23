package com.example.coordinator;

import com.example.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionCoordinator {
    private final RestTemplate restTemplate;


    public TransactionCoordinator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean executeTransaction(TransactionRequest request) {
        List<String> participants = request.getParticipants();
        List<String> preparedParticipants = new ArrayList<>();
        System.out.println("Coordinator : executeTransaction called <<<<<<<<<<<<<<<<< " + participants);

        try {
            for (String participant: participants) {
                ResponseEntity<String> response = restTemplate.postForEntity(participant + "/prepare",
                         request, String.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Prepare phase failed");
                }
                preparedParticipants.add(participant);
            }
            for (String participant : preparedParticipants) {
                restTemplate.postForEntity(participant + "/commit", request, Void.class);
            }
            return true;
        } catch (Exception e) {
            // Perform complete rollback
            for (String participant : preparedParticipants) {
                restTemplate.postForEntity(participant + "/rollback", request, String.class);
            }
            return false;
        }
    }
}
