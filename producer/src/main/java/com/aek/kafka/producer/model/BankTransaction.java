package com.aek.kafka.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {
    private Long id;
    private Long accountId;
    private Double amount;
    private TypeEnum type;
}

enum TypeEnum {
    TRANSFER, ONLINE, POS, DEPOSIT
}