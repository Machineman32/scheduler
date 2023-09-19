package com.example.todo.controllers;

import com.example.todo.models.Users.*;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.services.PhoneConfirmationService;
import com.example.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PhoneConfirmationService phoneConfirmationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/users")
    private ResponseEntity<List<UserDTO>> getAllUsers () {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping("/register")
    private ResponseEntity<UserDTO> createUser (@RequestBody UserRegisterDTO user) {
        return ResponseEntity.ok(this.userService.createUser(user));
    }

    @PutMapping("/recover/confirm")
    private ResponseEntity<String> recoverPassword (@RequestBody UserPhoneConfirmationDTO user) {
        return ResponseEntity.ok(this.userService.passwordRecovery(user));
    }

    @GetMapping({"/register/generateOTP", "/recover/generateOTP"})
    private ResponseEntity<String> generateOTP (@RequestBody String number) {
        return ResponseEntity.ok(this.phoneConfirmationService.generateOTP(number));
    }

    @GetMapping("/register/verifyOTP")
    private ResponseEntity<Boolean> verifyOTP (@RequestBody String phoneNumber, @RequestBody String OTP) {
        return ResponseEntity.ok(this.phoneConfirmationService.verifyOTP(phoneNumber, OTP));
    }

    @PostMapping("/login")
    private ResponseEntity<String> login (@RequestBody UserLoginDTO user) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        return ResponseEntity.ok("Bearer " + this.tokenProvider.generateToken(authentication));
    }

    @PutMapping("/accountSettings/{userId}")
    private ResponseEntity<UserDTO> accountSettings (@RequestBody UserRegisterDTO user, @PathVariable long userId) {
        return ResponseEntity.ok(this.userService.accountSettings(user, userId));
    }
}
