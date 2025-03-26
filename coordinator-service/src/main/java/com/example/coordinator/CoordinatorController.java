package com.example.coordinator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class CoordinatorController {
    private final TransactionCoordinator transactionCoordinator;

    public CoordinatorController(TransactionCoordinator transactionCoordinator) {
        this.transactionCoordinator = transactionCoordinator;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startTransaction(@RequestBody TransactionRequest request) {
        boolean success = transactionCoordinator.executeTransaction(request);
        return success ? ResponseEntity.ok("Transaction Committed") :
                ResponseEntity.status(500).body("Transaction Aborted");
    }
}
