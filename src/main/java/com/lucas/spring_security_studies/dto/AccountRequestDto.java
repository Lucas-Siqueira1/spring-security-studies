package com.lucas.spring_security_studies.dto;

import com.lucas.spring_security_studies.entities.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountRequestDto {

    private String accountNumber;
}
