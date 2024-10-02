package com.keduit.shop.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware {


  @Override
  public Optional<String> getCurrentAuditor() {
    // SecurityContext에서 현재 인증 객체를 가져옴
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = "";

    // 인증 객체가 null이 아닌지 확인
    if (authentication != null) {
      // 인증된 사용자의 이름(일반적으로 사용자 이름)을 가져옴
      userId = authentication.getName();
    }

    // userId를 Optional로 감싸서 반환
    return Optional.of(userId);
  }
}
