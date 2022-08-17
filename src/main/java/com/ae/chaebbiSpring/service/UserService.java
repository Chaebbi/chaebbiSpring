package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.SignupRequestDto;
import com.ae.chaebbiSpring.dto.request.UserUpdateRequestDto;
import com.ae.chaebbiSpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByKakaoId(String kakao) {
        return userRepository.findByKakao(kakao);
    }

    public void signup(Long id, SignupRequestDto dto) {
        userRepository.signup(id, dto);
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        userRepository.update(id, dto);
    }
}
