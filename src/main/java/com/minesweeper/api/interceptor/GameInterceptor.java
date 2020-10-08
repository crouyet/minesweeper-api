package com.minesweeper.api.interceptor;

import com.minesweeper.api.config.LocalStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class GameInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Clear storage because is possible that previous data remains on the threadLocal
        LocalStorage.clear();

        getUsername(request)
                .ifPresent( user -> LocalStorage.save(LocalStorage.USERNAME, user));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LocalStorage.clear();
    }

    private Optional<String> getUsername(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                        .flatMap(this::getUsernameFromCookie);
    }

    private Optional<String> getUsernameFromCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(LocalStorage.USERNAME))
                .findFirst()
                .map(Cookie::getValue);
    }
}
