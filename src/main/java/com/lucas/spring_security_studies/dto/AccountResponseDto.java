package com.lucas.spring_security_studies.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountResponseDto {

    private Integer id;
    private Double balance;
    private String accountNumber;
}
