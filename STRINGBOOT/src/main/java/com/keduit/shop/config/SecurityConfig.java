package com.keduit.shop.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 로그인과 관련.
// (로그인 처리와 인증을 담당) Spring Security 관련 설정이 이루어진 파일로, 로그인과 회원가입 시 인증과 권한 관리 등을 설정하는 역할.

@Configuration // 이 클래스가 Spring의 설정 클래스.
@EnableWebSecurity // Spring Security 기능을 활성화.
public class SecurityConfig {

  @Bean // 이 메서드가 Spring의 Bean으로 등록된다는 것을 나타냄.
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    System.out.println(
        "-------------SecurityFilterChain---------------"); // SecurityFilterChain이 생성될 때 콘솔에 출력.

    // HttpSecurity 설정
    http.formLogin() // 폼 기반 로그인 설정을 사용.
        .loginPage("/members/login") // 사용자 정의 로그인 페이지의 경로.
        .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트될 기본 URL.
        .usernameParameter("email") // 로그인 시 사용할 사용자 이름(이메일) 파라미터.
        .failureUrl("/members/login/error") // 로그인 실패 시 리다이렉트될 URL.
        .and() // 이전 설정의 끝을 나타냄.
        .logout() // 로그아웃 설정을 시작.
        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 요청을 처리할 URL.
        .logoutSuccessUrl("/"); // 로그아웃 성공 후 리다이렉트될 URL.

    // permitAll() : 모든 사용자가 인증없이 해당 경로에 접근 가능
    // hasRole() : ADMIN  가진 사용자만 해당 경로에 접근할 수 있도록 경로를 통과시킴.
    // anyRequest().authenticated() : 로그인이 성공한 사용자만 해당 경로에 접근할 수 있도록 설정. 위의 경우 이외에 페이지는 인증절차가 필요함.

    http.authorizeRequests()
        .mvcMatchers("/", "/members/**", "/item/**", "error", "favicon.ico").permitAll()
        .mvcMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated();

    // 인증되지 않은 사용자가 리소스에 접근허여 살패했을때 처리하는 핸들러 등록.
    http.exceptionHandling()
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint());// 403 에러 페이지.
    return http.build(); // 설정을 적용하고 SecurityFilterChain 객체를 반환.
  }

  @Bean // 이 메서드도 Spring의 Bean으로 등록된다는 것을 나타냄.
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 인코딩을 위한 BCryptPasswordEncoder를 반환.
  }

  // resources/static 리소스(CSS, JS, IMG)를 Spring Security 에서 제외. = 폴더의 하위 파일은 인증에서 제외.
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return null;
  }
}

