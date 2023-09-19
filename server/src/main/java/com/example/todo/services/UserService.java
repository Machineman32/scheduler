package com.example.todo.services;

import com.example.todo.entities.UserEntity;
import com.example.todo.exceptions.*;
import com.example.todo.models.Users.UserPhoneConfirmationDTO;
import com.example.todo.models.Users.UserRegisterDTO;
import com.example.todo.models.Users.UserDTO;
import com.example.todo.other.Utils;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PhoneConfirmationService phoneConfirmationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers () {
        List<UserDTO> usersDTO = new ArrayList<>();
        List<UserEntity> userEntities = this.userRepository.findAll();

        for(UserEntity userEntity : userEntities) {
            usersDTO.add(new UserDTO(userEntity));
        }

        return usersDTO;
    }

    public UserDTO createUser (UserRegisterDTO userRegisterDTO) {

        if(this.userRepository.findUserByPhoneNumber(userRegisterDTO.getPhoneNumber()).isPresent()) {
            throw new AlreadyExistingException("You have already signed up with this phone number");
        }

        if(!this.phoneConfirmationService.verifyOTP(userRegisterDTO.getPhoneNumber(), userRegisterDTO.getOTP())) {
            throw new PhoneConfirmationException("Invalid OTP code. Please try again");
        }

        UserEntity user = new UserEntity(
                userRegisterDTO.getUsername(),
                this.passwordEncoder.encode(userRegisterDTO.getPassword()),
                userRegisterDTO.getName(),
                Utils.trimPhoneNumberFormat(userRegisterDTO.getPhoneNumber())
        );
        this.userRepository.save(user);

        return userRegisterDTO;
    }


    //still working it out
    public String passwordRecovery (UserPhoneConfirmationDTO user) {
        Optional<UserEntity> userEntityOpt = this.userRepository.findUserByPhoneNumber(Utils.trimPhoneNumberFormat(user.getPhoneNumber()));

        if(userEntityOpt.isEmpty()) {
            throw new RuntimeException("Phone number not registered. Please try with a valid number");
        }

        String number = Utils.trimPhoneNumberFormat(user.getPhoneNumber());
        UserEntity userEntity = userEntityOpt.get();

        String newPass = Utils.passwordGenerator();
        userEntity.setPassword(this.passwordEncoder.encode(newPass));

        this.phoneConfirmationService.sendNewPassword(number, newPass);
        this.userRepository.save(userEntity);

        return "We've send you a SMS with a new generated password. Please check your mobile device";
    }

    public UserDTO accountSettings (UserRegisterDTO user, long id) {
        Optional<UserEntity> userEntityOpt = this.userRepository.findById(id);
        if(userEntityOpt.isEmpty()) {
            throw new RuntimeException("User not found. Please try again");
        }

        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userEntityOpt.get();

        if(!loggedUsername.equals(userEntityOpt.get().getUsername())) {
            throw new ForbiddenActionException("You are not allowed todo this action");
        }
        userEntity.setName(user.getName());

        if(!user.getPassword().equals(user.getRetypePassword())) {
            throw new MismatchingPasswordsException();
        }
        userEntity.setPassword(this.passwordEncoder.encode(user.getPassword()));

        if(this.userRepository.findUserByPhoneNumber(user.getPhoneNumber()).isPresent() && userEntity.getUsername().equals(user.getUsername())) {
            throw new AlreadyExistingException("This username is already in use");
        }

        userEntity.setUsername(user.getUsername());

        this.userRepository.save(userEntity);
        return new UserDTO(userEntity);
    }
}
