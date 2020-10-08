package com.minesweeper.api.interceptor;

import com.minesweeper.api.config.LocalStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GameInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Clear storage because is possible that previous data remains on the threadLocal
        LocalStorage.clear();

        LocalStorage.save(LocalStorage.USERNAME, request.getParameter(LocalStorage.USERNAME));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LocalStorage.clear();
    }
}
