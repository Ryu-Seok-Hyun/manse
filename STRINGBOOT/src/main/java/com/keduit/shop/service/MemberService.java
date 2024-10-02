package com.keduit.shop.service;
//맴버리파짓토리 만든후에 옴.

import com.keduit.shop.entity.Member;
import com.keduit.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 로그인과 관련.
// (비즈니스 로직을 처리) 회원 관련 비즈니스 로직을 처리하는 서비스로, 회원가입 시 회원 정보 저장, 로그인 시 인증 정보 검증 등의 기능을 수행.

@Service // 이 클래스는 서비스 컴포넌트임을 나타내며, 비즈니스 로직을 처리
@Transactional // 이 클래스의 모든 메서드는 트랜잭션이 적용됨
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성해줌
public class MemberService implements UserDetailsService { // UserDetailsService를 구현하여 사용자 인증을 처리

  private final MemberRepository memberRepository; // 회원 정보를 데이터베이스에서 가져오는 리포지토리

  // 회원 정보를 저장하는 메서드
  public Member saveMember(Member member) {
    validateMember(member); // 회원 정보 유효성 검사
    return memberRepository.save(member); // 유효한 회원 정보를 데이터베이스에 저장
  }

  // 회원 유효성 검사 메서드
  private void validateMember(Member member) {
    Member findMember = memberRepository.findByEmail(member.getEmail()); // 이메일로 이미 가입된 회원 찾기
    if (findMember != null) { // 이미 존재하면 예외 발생
      throw new IllegalStateException("이미 가입된 회원 입니다."); // 중복 회원 가입 예외
    }
  }

  // 이메일로 회원 정보를 로드하는 메서드 (UserDetailsService 인터페이스 구현)
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email); // 이메일로 회원 찾기
    System.out.println("-------member =>" + member); // 찾은 회원 정보 출력

    if (member == null) { // 회원이 없으면 예외 발생
      throw new UsernameNotFoundException(email); // 사용자 찾을 수 없음 예외
    }

    // UserDetails 객체 생성하여 반환
    return User.builder().username(member.getEmail()) // 사용자 이름은 이메일
        .password(member.getPassword()) // 비밀번호
        .roles(member.getRole().toString()) // 역할
        .build(); // UserDetails 객체 반환
  }
}

