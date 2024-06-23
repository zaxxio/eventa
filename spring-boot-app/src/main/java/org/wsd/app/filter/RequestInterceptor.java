package org.wsd.app.filter;

import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    private final String REQUEST_ID = "X-Request-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(REQUEST_ID);
        if (StringUtil.isNullOrEmpty(userId)) {
            MDC.put("REQUEST_ID", UUID.randomUUID().toString());
        } else {
            MDC.put("REQUEST_ID", userId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove("REQUEST_ID");
    }
}