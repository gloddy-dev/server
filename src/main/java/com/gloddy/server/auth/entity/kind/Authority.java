package com.gloddy.server.auth.entity.kind;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ;

    private final String role;
}
