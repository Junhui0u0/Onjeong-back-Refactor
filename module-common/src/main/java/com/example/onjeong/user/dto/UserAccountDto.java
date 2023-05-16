package com.example.onjeong.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserAccountDto {
    private String userName;
    private String userPassword;
    private String userStatus;
    private LocalDate userBirth;
    private String userEmail;
}
