package com.gloddy.server.apply.service;

import com.gloddy.server.apply.dto.ApplyRequest;
import com.gloddy.server.apply.dto.ApplyResponse;
import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final UserRepository userRepository;
    private final GroupJpaRepository groupJpaRepository;

    // TODO: user exception 처리
    @Transactional
    public ApplyResponse.create createApply(Long userId, Long groupId, ApplyRequest.create request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저 없음"));
        Group group = groupJpaRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("그룹 없음"));
        Apply apply = applyJpaRepository.save(
            Apply.builder()
                .user(user)
                .group(group)
                .content(request.getIntroduce())
                .reason(request.getReason())
                .build()
        );
        return new ApplyResponse.create(apply.getId());
    }

    // TODO: exception 처리
    @Transactional
    public void deleteApply(Long userId, Long groupId) {
        Apply apply = applyJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지원서"));
        applyJpaRepository.delete(apply);
    }

    // TODO: exception 처리
    @Transactional
    public void updateStatusApply(Long userId, Long groupId, Long applyId, Status status) {
        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지원서"));
        if(checkCaptain(groupId, userId)){
            apply.updateStatus(status);
        }
    }

    public Boolean checkCaptain(Long groupId, Long userId) {
        Group group = groupJpaRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 그룹"));
        if(group.getUser().getId().equals(userId)){
            return true;
        }
        return false;
    }
}
