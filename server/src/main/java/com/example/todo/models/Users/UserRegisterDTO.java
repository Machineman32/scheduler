package com.example.todo.models.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserRegisterDTO extends UserDTO {
    private String name;
    private String username;
    private String password;
    private String retypePassword;
    private String phoneNumber;
    private String OTP;
}
