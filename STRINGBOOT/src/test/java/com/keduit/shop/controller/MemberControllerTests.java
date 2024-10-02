package com.keduit.shop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.entity.Member;
import com.keduit.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// 스프링 부트 테스트 설정
@SpringBootTest
@AutoConfigureMockMvc // MockMvc를 자동 설정
@Transactional // 각 테스트 후 데이터 롤백
public class MemberControllerTests {

  @Autowired
  private MemberService memberService; // 회원 서비스 주입

  @Autowired
  private MockMvc mockMvc; // MockMvc 주입 (HTTP 요청을 모의할 때 사용)

  @Autowired
  private PasswordEncoder passwordEncoder; // 비밀번호 인코더 주입

  // 로그인 성공 테스트
  @Test
  @DisplayName("로그인 성공 테스트")
  public void loginSuccessTest() throws Exception {
    String email = "tester2@test.com"; // 테스트용 이메일
    String password = "1234"; // 테스트용 비밀번호
    this.createMember(email, password); // 회원 생성
    // 로그인 요청을 모의하고 인증 성공 확인
    mockMvc.perform(formLogin().userParameter("email").loginProcessingUrl("/members/login")
            .user(email).password(password))
        .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 인증된 상태 확인
  }

  // 회원 생성 메서드 (테스트용)
  private Member createMember(String email, String password) {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setAddress("서울 동작구 보라매동"); // 주소 설정
    memberDTO.setName("한정교2"); // 이름 설정
    memberDTO.setEmail(email); // 이메일 설정
    memberDTO.setPassword(password); // 비밀번호 설정
    Member member = Member.createMember(memberDTO, passwordEncoder); // Member 엔티티 생성
    return memberService.saveMember(member); // 회원 저장
  }

  // 로그인 실패 테스트
  @Test
  @DisplayName("로그인 실패 테스트")
  public void loginFailTest() throws Exception {
    String email = "1@1.com"; // 테스트용 이메일
    String password = "1234"; // 테스트용 비밀번호
    this.createMember(email, password); // 회원 생성

    // 잘못된 비밀번호로 로그인 요청을 모의하고 인증 실패 확인
    mockMvc.perform(formLogin().userParameter("email").loginProcessingUrl("/members/login")
            .user(email).password("123456"))
        .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); // 인증되지 않은 상태 확인
  }
}