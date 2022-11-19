package com.gloddy.server.auth.service;

import com.gloddy.server.Mate.repository.MateJpaRepository;
import com.gloddy.server.auth.dto.UserResponse;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserHandler userHandler;
    private final MateJpaRepository mateJpaRepository;

    // TODO: 칭찬 수, 자기소개, 성격 response에 추가
    public UserResponse.MyPage getMyPage(Long userId) {
        User user = userHandler.findById(userId);
        Long reviewCount = mateJpaRepository.countMateByUser(user);
        return new UserResponse.MyPage(
                user.getImageUrl(),
                user.getName(),
                user.getGender().getValue(),
                user.getSchool(),
                user.getScore(),
                reviewCount
        );
    }
}
