package com.lucas.spring_security_studies.dto;

import com.lucas.spring_security_studies.entities.Account;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TransactionResponseDto {

    private Integer id;
    private Integer senderAccountId;
    private Integer receiveAccountId;
    private Double value;
    private Instant moment;
}
