package com.lucas.spring_security_studies.dto;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TransactionRequestDto {

    private Double value;
    private Integer receiveAccountId;
    private Integer senderAccountId;
}
