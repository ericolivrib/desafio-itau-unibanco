package com.erico.desafio.itau.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class RequestLoggingInterceptor implements HandlerInterceptor {
  
  private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
  private static final String START_TIME_ATTRIBUTE_KEY = "startTime";
  private static final String USER_AGENT_HEADER_KEY = "User-Agent";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    request.setAttribute(START_TIME_ATTRIBUTE_KEY, System.currentTimeMillis());
    log.info("{} {} - IP: {} - User Agent: {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), request.getHeader(USER_AGENT_HEADER_KEY));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    long startTime = (long) request.getAttribute(START_TIME_ATTRIBUTE_KEY);
    long duration = System.currentTimeMillis() - startTime;

    String logMessage = "{} {} - Duration: {}ms - Status: {} {} - IP: {} - User Agent: {}";
    Object[] logArgs = {
      request.getMethod(),
      request.getRequestURI(),
      duration,
      response.getStatus(),
      Objects.requireNonNull(HttpStatus.resolve(response.getStatus())).name().replace("_", " "),
      request.getRemoteAddr(),
      request.getHeader(USER_AGENT_HEADER_KEY)
    };

    if (ex != null) log.info(logMessage, logArgs);
    else log.error(logMessage, logArgs);
  }
}
