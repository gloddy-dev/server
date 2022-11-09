package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class UserBusinessException extends BaseBusinessException{

    public UserBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
