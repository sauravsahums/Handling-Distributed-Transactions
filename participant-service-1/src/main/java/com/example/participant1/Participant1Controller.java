package com.example.participant1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant1")
public class Participant1Controller {
    private final ParticipantService participantService;

    public Participant1Controller(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/prepare")
    public ResponseEntity<String> prepare(@RequestBody TransactionRequest request) {
        System.out.println("prepare called 1 <<<<<<<<<<<<<<<<< ");
        boolean prepared = participantService.prepare(request);
        return prepared ? ResponseEntity.ok("Prepared") : ResponseEntity.status(500).body("Abort");
    }

    @PostMapping("/commit")
    public ResponseEntity<String> commit(@RequestBody TransactionRequest request) {
        System.out.println("commit called  1 <<<<<<<<<<<<<<<<< ");

        participantService.commit(request);
        return ResponseEntity.ok("Committed");
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> rollback(@RequestBody TransactionRequest request) {
        System.out.println("rollback called 1 <<<<<<<<<<<<<<<<< ");

        participantService.rollback(request);
        return ResponseEntity.ok("Rolled Back");
    }
}
