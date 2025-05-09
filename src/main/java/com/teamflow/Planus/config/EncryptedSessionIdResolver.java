package com.teamflow.Planus.config;

import com.teamflow.Planus.util.TokenEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.List;
import java.util.stream.Collectors;

public class EncryptedSessionIdResolver implements HttpSessionIdResolver {

    private final CookieHttpSessionIdResolver delegate = new CookieHttpSessionIdResolver();

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        List<String> encrypted = delegate.resolveSessionIds(request);
        return encrypted.stream()
                .map(TokenEncryptor::decrypt)
                .collect(Collectors.toList());
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        String encrypted = TokenEncryptor.encrypt(sessionId);
        delegate.setSessionId(request, response, encrypted);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        delegate.expireSession(request, response);
    }

}
