package com.minesweeper.api.interceptor;

import com.minesweeper.api.config.RSD;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class GameInterceptor extends HandlerInterceptorAdapter {

    private static final String SET_COOKIE = "Set-Cookie";
    private static final Integer MAX_AGE_DEFAULT = 365 * 24 * 60 * 60 * 1000;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Clear storage because is possible that previous data remains on the threadLocal
        RSD.clear();

        getUsername(request)
                .ifPresent( user -> RSD.save(RSD.USERNAME, user));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

        if(Objects.nonNull(RSD.get(RSD.USERNAME))) {
            this.addCookie(response, generateCookie(RSD.USERNAME, RSD.get(RSD.USERNAME), MAX_AGE_DEFAULT));
        } else {
            this.addCookie(response, generateCookie(RSD.USERNAME, "", 0));
        }

        RSD.clear();
    }

    private Optional<String> getUsername(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                        .flatMap(this::getUsernameFromCookie);
    }

    private Optional<String> getUsernameFromCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(RSD.USERNAME))
                .findFirst()
                .map(Cookie::getValue);
    }


    private void addCookie(HttpServletResponse response, Cookie cookie){
        if(response.getHeaders(SET_COOKIE)
                .stream()
                .noneMatch(responseCookie -> responseCookie.startsWith(cookie.getName()))) {

            response.addHeader(SET_COOKIE, cookie.getValue());
        }
    }

    private Cookie generateCookie(String cookieName, String cookieValue, int maxAge) {

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setSecure(Boolean.TRUE);
        cookie.setHttpOnly(Boolean.TRUE);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }
}
