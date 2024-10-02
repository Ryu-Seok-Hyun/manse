package com.keduit.shop.config;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인과 관련.
// (로그인 처리와 인증을 담당) 인증되지 않은 사용자 접근 시 처리하는 파일일 가능성이 높음.

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    // AJAX 비동기 통신의 경우 HTTP 요청 헤더에 "X-Requested-With" 값이 "XMLHttpRequest"인지 확인
    // 인증되지 않은 사용자가 AJAX로 리소스를 요청하면 "Unauthorized(401)" 에러를 발생시킴
    if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
      response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "UnAuthorized");
    } else {
      response.sendRedirect("/members/login");
    }
  }
}
