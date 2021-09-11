package com.framework.blogapi.service;

import com.framework.blogapi.dto.UserRequestDTO;
import com.framework.blogapi.exceptions.UserExistsException;
import com.framework.blogapi.model.User;
import com.framework.blogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public void create(UserRequestDTO userRequestDTO){
        Optional<User> userOpt = repository.findByEmail(userRequestDTO.getEmail());
        if(userOpt.isPresent()) throw new UserExistsException();
        repository.save( User.builder()
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .password( new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()))
                .build() );
    }
}
