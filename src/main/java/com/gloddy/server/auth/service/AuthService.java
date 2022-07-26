package com.gloddy.server.auth.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.auth.dto.AuthRequest;
import com.gloddy.server.auth.dto.AuthResponse;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Transactional
    public AuthResponse.SignUp signUp(AuthRequest.SignUp req) {

        List<Personality> personalities = convertStringToPersonalityEnum(req);

        User created = User.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .school(req.getSchool())
                .birth(req.getBirth())
                .gender(req.getGender())
                .personalities(personalities)
                .build();

        User user = userRepository.save(created);

        return new AuthResponse.SignUp(user.getId(), user.getAuthority().getRole());

    }

    private List<Personality> convertStringToPersonalityEnum(AuthRequest.SignUp req) {
        return req.getPersonalities().stream()
                .map(personality -> Personality.valueOf(personality))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public AuthResponse.Login login(AuthRequest.Login req) {

        User findUser = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        if (!req.getPassword().equals(findUser.getPassword())) {
            throw new UserBusinessException(ErrorCode.PASSWORD_DISCORD);
        }

        String token = jwtTokenBuilder.createToken(req.getEmail());

        return new AuthResponse.Login(findUser.getId(), findUser.getAuthority().getRole(), token);
    }

    @Transactional(readOnly = true)
    public AuthResponse.Whether emailCheck(String email) {

        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isEmpty()) {
            return new AuthResponse.Whether(false);
        } else {
            return new AuthResponse.Whether(true);
        }
    }
}
