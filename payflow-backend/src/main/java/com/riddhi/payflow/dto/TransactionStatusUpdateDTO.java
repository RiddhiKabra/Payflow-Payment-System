package com.riddhi.payflow.dto;

import com.riddhi.payflow.entity.TransactionStatus;
import lombok.Data;

@Data
public class TransactionStatusUpdateDTO {

    private TransactionStatus status;
}