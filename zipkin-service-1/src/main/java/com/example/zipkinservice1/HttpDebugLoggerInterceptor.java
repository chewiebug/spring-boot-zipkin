package com.example.zipkinservice1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class HttpDebugLoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        log.info("headers: {}", formatHeaders(request));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String formatHeaders(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> stringBuilder.append(headerName)
                .append(":").append(request.getHeader(headerName)).append("\n"));

        return stringBuilder.toString();
    }

}
