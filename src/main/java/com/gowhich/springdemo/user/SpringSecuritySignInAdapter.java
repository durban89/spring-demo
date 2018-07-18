package com.gowhich.springdemo.user;

// import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public final class SpringSecuritySignInAdapter implements SignInAdapter {

    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
        return null;
    }

}