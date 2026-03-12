package com.riddhi.payflow.service;

import com.riddhi.payflow.dto.TransactionRequestDTO;
import com.riddhi.payflow.dto.TransactionResponseDTO;
import com.riddhi.payflow.entity.Transaction;
import com.riddhi.payflow.entity.TransactionStatus;
import com.riddhi.payflow.entity.User;
import com.riddhi.payflow.exception.ResourceNotFoundException;
import com.riddhi.payflow.repository.TransactionRepository;
import com.riddhi.payflow.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    // ✅ REPOSITORIES MUST BE CLASS FIELDS
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private static final int MAX_RETRY = 3;

    // ✅ Constructor Injection
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create transaction for logged-in user
     */
    public TransactionResponseDTO createTransaction(
            String email,
            TransactionRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .status(TransactionStatus.CREATED)
                .referenceId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return mapToDTO(savedTransaction);
    }

    /**
     * Process transaction (simulate payment gateway)
     */
    public TransactionResponseDTO processTransaction(Long transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        if (transaction.getRetryCount() >= MAX_RETRY) {
            throw new RuntimeException("Retry limit exceeded");
        }

        transaction.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transaction);

        // simulate gateway
        boolean paymentSuccess = Math.random() > 0.5;

        if (paymentSuccess) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setRetryCount(transaction.getRetryCount() + 1);
        }

        Transaction updated = transactionRepository.save(transaction);

        return mapToDTO(updated);
    }

    /**
     * Get logged-in user transactions
     */

    public Page<Transaction> getMyTransactions(String email, Pageable pageable) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return transactionRepository.findByUser(user, pageable);
    }

    private TransactionResponseDTO mapToDTO(Transaction transaction) {

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getStatus().name(),
                transaction.getReferenceId(),
                transaction.getCreatedAt()
        );
    }

    @Async
    public void processTransactionAsync(Long transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        transaction.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transaction);

        try {
            Thread.sleep(3000); // simulate gateway delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean paymentSuccess = Math.random() > 0.4;

        if (paymentSuccess) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        transactionRepository.save(transaction);
    }

}