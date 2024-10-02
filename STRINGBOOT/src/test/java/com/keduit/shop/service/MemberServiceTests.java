package com.keduit.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.keduit.shop.constant.Role;
import com.keduit.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // 스프링 부트 통합 테스트를 위한 설정
@Transactional // 테스트 후 데이터베이스 상태를 롤백하여 초기화
public class MemberServiceTests {

  @Autowired
  MemberService memberService; // 회원 관련 비즈니스 로직을 처리하는 서비스 주입

  @Autowired
  PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더 주입

  // 회원 가입 기능 테스트
  @Test
  @DisplayName("회원 가입 테스트") // 테스트의 설명을 설정
  public void saveMemberTest() {
    Member member = new Member(); // 새 회원 객체 생성
    member.setPassword("1234"); // 비밀번호 설정
    member.setEmail("member11@member.com"); // 이메일 설정
    member.setAddress("동작구 보라매동"); // 주소 설정
    member.setName("한정교"); // 회원 이름 설정
    member.setRole(Role.USER); // 사용자 역할 설정

    // 회원 정보를 저장하고 결과를 savedMember에 저장
    Member savedMember = memberService.saveMember(member);
    System.out.println(savedMember); // 저장된 회원 정보를 콘솔에 출력

    // 저장된 회원 정보가 입력한 정보와 일치하는지 확인
    assertEquals(member.getEmail(), savedMember.getEmail()); // 이메일 확인
    assertEquals(member.getName(), savedMember.getName()); // 이름 확인
    assertEquals(member.getPassword(), savedMember.getPassword()); // 비밀번호 확인
    assertEquals(member.getAddress(), savedMember.getAddress()); // 주소 확인
    assertEquals(member.getRole(), savedMember.getRole()); // 역할 확인
  }

}
