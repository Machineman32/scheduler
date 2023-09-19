package com.example.todo.models.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserPhoneConfirmationDTO {
    private String phoneNumber;
    private String OTP;
}
