package com.lucas.spring_security_studies.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserRequestDto {

    private String name;
    private String email;
    private String password;
}
