package com.gloddy.server.authEmail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthEmailResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCode {
        private boolean isVerify;
    }
}
