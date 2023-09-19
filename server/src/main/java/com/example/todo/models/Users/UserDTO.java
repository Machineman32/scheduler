package com.example.todo.models.Users;

import com.example.todo.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String phoneNumber;

    public UserDTO (UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.name = userEntity.getName();
        this.phoneNumber = userEntity.getPhoneNumber();
    }
}
