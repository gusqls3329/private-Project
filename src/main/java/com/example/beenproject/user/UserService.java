package com.example.beenproject.user;

import com.example.beenproject.eneities.User;
import com.example.beenproject.user.dto.SignUpDto;
import com.example.beenproject.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public int postSignup(SignUpDto dto){

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNick(dto.getNick());
        user.setPic(dto.getPic());
    }
}
