package com.riddhi.payflow.controller;

import com.riddhi.payflow.dto.ApiResponse;
import com.riddhi.payflow.dto.TransactionRequestDTO;
import com.riddhi.payflow.dto.TransactionResponseDTO;
import com.riddhi.payflow.entity.Transaction;
import com.riddhi.payflow.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //Create Transaction (Logged-in User)

    @PostMapping
    public ApiResponse<TransactionResponseDTO> createTransaction(
            Authentication authentication,
            @RequestBody TransactionRequestDTO dto) {

        String email = authentication.getName();

        TransactionResponseDTO transaction =
                transactionService.createTransaction(email, dto);

        return new ApiResponse<>(
                true,
                "Transaction created successfully",
                transaction
        );
    }

    //Get Logged-in User Transactions

    @GetMapping("/my")
    public ApiResponse<Page<Transaction>> getMyTransactions(
            Authentication authentication,
            Pageable pageable) {

        String email = authentication.getName();

        Page<Transaction> transactions =
                transactionService.getMyTransactions(email, pageable);

        return new ApiResponse<>(
                true,
                "Transactions fetched successfully",
                transactions
        );
    }

    // Process Transaction (simulate payment)
    @PostMapping("/process/{transactionId}")
    public ApiResponse<String> processTransaction(
            @PathVariable Long transactionId) {

        transactionService.processTransactionAsync(transactionId);

        return new ApiResponse<>(
                true,
                "Transaction is being processed asynchronously",
                "PROCESSING_STARTED"
        );
    }

    @PostMapping("/retry/{transactionId}")
    public ApiResponse<TransactionResponseDTO> retryTransaction(
            @PathVariable Long transactionId) {

        TransactionResponseDTO response =
                transactionService.processTransaction(transactionId);

        return new ApiResponse<>(
                true,
                "Retry attempted",
                response
        );
    }
}