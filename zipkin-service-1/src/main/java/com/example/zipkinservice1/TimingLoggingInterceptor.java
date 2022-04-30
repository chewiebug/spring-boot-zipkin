package com.example.zipkinservice1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TimingLoggingInterceptor implements HandlerInterceptor {
    private final ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        log.info("starting request for {}", request.getRequestURI());

        stopWatchThreadLocal.set(new StopWatch());
        stopWatchThreadLocal.get().start();

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        StopWatch stopWatch = stopWatchThreadLocal.get();
        stopWatch.stop();

        log.info("request {} took {} ms ({} nanos)", request.getRequestURI(), stopWatch.getLastTaskTimeMillis(), stopWatch.getLastTaskTimeNanos());
        stopWatchThreadLocal.remove();

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
