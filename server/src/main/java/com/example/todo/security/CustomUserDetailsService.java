package com.example.todo.security;

import com.example.todo.entities.UserEntity;
import com.example.todo.models.Users.UserAuthDTO;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Override
    public UserAuthDTO loadUserByUsername(String username) {
        Optional<UserEntity> optionalUser = userDao.findUserByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username or email: "+ username);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new UserAuthDTO(optionalUser.get().getUsername(), optionalUser.get().getPassword(), authorities);

    }
}
