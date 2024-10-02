package com.keduit.shop.repository;


import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest // 스프링 부트 통합 테스트 설정
public class MemberRepositoryTests {

  @Autowired
  MemberRepository memberRepository; // 회원 관련 DB 작업을 위한 레포지토리 주입

  @Autowired
  PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더 주입

  // 회원 가입 기능 테스트
  @Test
  @DisplayName("회원 가입 테스트") // 테스트의 이름을 설정
  public void createMemberTest() {
    MemberDTO member = new MemberDTO(); // 새 회원 정보를 담을 DTO 객체 생성
    member.setName("테스터"); // 회원 이름 설정
    member.setEmail("tester@test.com"); // 이메일 설정
    member.setPassword("1234"); // 비밀번호 설정
    member.setAddress("동작구 보라매동"); // 주소 설정

    // Member 객체를 생성하고 비밀번호를 암호화
    Member mem = Member.createMember(member, passwordEncoder);

    // 생성된 회원 정보를 DB에 저장
    memberRepository.save(mem);
  }

}
