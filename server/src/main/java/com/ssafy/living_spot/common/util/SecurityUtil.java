package com.ssafy.living_spot.common.util;

import com.ssafy.living_spot.common.exception.ErrorMessage;
import com.ssafy.living_spot.common.exception.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getAuthenticatedMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UnAuthorizedException(ErrorMessage.NOT_AUTHORIZED_MEMBER);
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Long)) {
            throw new RuntimeException(ErrorMessage.INVALID_PRINCIPAL_TYPE.getMessage());
        }
        return (Long) principal;
    }
}
