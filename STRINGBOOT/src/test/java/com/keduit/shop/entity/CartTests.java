package com.keduit.shop.entity;

// 장바구니임


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.repository.CartRepository;
import com.keduit.shop.repository.MemberRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // 스프링 부트 테스트를 위한 설정
@Transactional // 각 테스트 후 데이터 롤백
public class CartTests {

  @Autowired
  CartRepository cartRepository; // 장바구니 레포지토리 주입

  @Autowired
  MemberRepository memberRepository; // 회원 레포지토리 주입

  @Autowired
  PasswordEncoder passwordEncoder; // 비밀번호 인코더 주입

  @PersistenceContext
  EntityManager em; // 엔티티 매니저 주입 (데이터베이스와의 상호작용을 위해 사용)

  // 테스트용 회원 생성 메서드
  public Member createMember() {
    MemberDTO memberDTO = new MemberDTO(); // MemberDTO 객체 생성
    memberDTO.setPassword("1234"); // 비밀번호 설정
    memberDTO.setName("홍길동"); // 이름 설정
    memberDTO.setEmail("hong@hong.com"); // 이메일 설정
    memberDTO.setAddress("서울시 동작구 대방동"); // 주소 설정
    return Member.createMember(memberDTO, passwordEncoder); // Member 엔티티 생성 및 반환
  }

  // 장바구니와 회원 엔티티 조회 테스트
  @Test
  @DisplayName("장바구니 회원 엔티티 조회 테스트")
  public void findCartAndMemberTest() {
    Member member = createMember(); // 테스트용 회원 생성
    memberRepository.save(member); // 회원 저장

    Cart cart = new Cart(); // 장바구니 객체 생성
    cart.setMember(member); // 생성한 회원을 장바구니에 설정
    cartRepository.save(cart); // 장바구니 저장

    em.flush(); // 변경 내용을 데이터베이스에 즉시 반영
    em.clear(); // 영속성 컨텍스트 초기화

    // 저장한 장바구니를 ID로 조회
    Cart savedCart = cartRepository.findById(cart.getId())
        .orElseThrow(EntityNotFoundException::new); // 존재하지 않을 경우 예외 발생

    // 저장한 장바구니의 회원 ID와 원래 회원 ID가 같은지 확인
    assertEquals(savedCart.getMember().getId(), member.getId());
  }
}
