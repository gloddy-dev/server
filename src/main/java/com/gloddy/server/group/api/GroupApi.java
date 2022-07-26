package com.gloddy.server.group.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupApi {

    private final GroupService groupService;

    @PostMapping("/group-create")
    public ResponseEntity createGroup(@AuthenticationPrincipal Long captainId,
                                      @RequestBody GroupRequest.Create req) {
        GroupResponse.Create response = groupService.createGroup(captainId, req);
        return ApiResponse.ok(response);
    }

    @GetMapping("/groups")
    public PageResponse<GroupResponse.GetGroup> getGroups(
            @AuthenticationPrincipal Long userId,
            @RequestParam int size,
            @RequestParam int page
    ) {
        return groupService.getGroups(userId, size, page);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity getGroupDetail(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long groupId
    ) {
        GroupResponse.GetGroupDetail response = groupService.getGroupDetail(userId, groupId);
        return ApiResponse.ok(response);
    }
}
