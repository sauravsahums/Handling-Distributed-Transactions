package com.example.participant;

import com.example.common.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/prepare")
    public ResponseEntity<String> prepare(@RequestBody TransactionRequest request) {
        boolean prepared = participantService.prepare(request);
        return prepared ? ResponseEntity.ok("Prepared") : ResponseEntity.status(500).body("Abort");
    }

    @PostMapping("/commit")
    public ResponseEntity<String> commit(@RequestBody TransactionRequest request) {
        participantService.commit(request);
        return ResponseEntity.ok("Committed");
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> rollback(@RequestBody TransactionRequest request) {
        participantService.rollback(request);
        return ResponseEntity.ok("Rolled Back");
    }
}
