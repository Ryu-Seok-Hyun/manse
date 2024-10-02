package com.keduit.shop.repository;
//회원과정 만듬

import com.keduit.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// 로그인과 관련.
// (데이터베이스와의 소통을 담당)회원 정보를 DB와 소통하는 레포지토리로, 회원가입 시 회원 정보를 저장하고, 로그인 시 사용자를 조회하는 역할을 담당.

public interface MemberRepository extends JpaRepository<Member, Long> {
  // Member 엔티티에 대한 CRUD 작업을 지원하는 리포지토리 인터페이스

  // 이메일로 회원 정보를 찾는 메서드
  Member findByEmail(String email);
}
