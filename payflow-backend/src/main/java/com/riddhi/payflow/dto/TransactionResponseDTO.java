package com.riddhi.payflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponseDTO {

    private Long id;
    private Double amount;
    private String status;
    private String referenceId;
    private LocalDateTime createdAt;
}