package com.keduit.shop.entity;

import com.keduit.shop.repository.MemberRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

@SpringBootTest // 스프링 부트 테스트 설정
@Transactional // 각 테스트 후 데이터 롤백
public class MemberTests {

  @Autowired
  MemberRepository memberRepository; // 회원 레포지토리 주입

  @PersistenceContext
  EntityManager em; // 엔티티 매니저 주입 (데이터베이스와의 상호작용을 위해 사용)

  // Auditing 기능 테스트
  @Test
  @DisplayName("Auditing 테스트") // 테스트 이름 설정
  @WithMockUser(username = "keduit", roles = "USER") // 사용자 권한 설정 (모의 사용자)
  public void auditingTest() {
    Member newMember = new Member(); // 새 회원 객체 생성
    memberRepository.save(newMember); // 회원 저장

    em.flush(); // 변경 내용을 데이터베이스에 즉시 반영
    em.clear(); // 영속성 컨텍스트 초기화

    // 저장한 회원을 ID로 조회
    Member member = memberRepository.findById(newMember.getId())
        .orElseThrow(EntityNotFoundException::new); // 존재하지 않을 경우 예외 발생

    // 등록 시간, 수정 시간, 생성자, 수정자를 출력
    System.out.println("register time: " + member.getRegTime());
    System.out.println("update time: " + member.getUpdateTime());
    System.out.println("create member: " + member.getCreatedBy());
    System.out.println("update member: " + member.getModifiedBy());
  }

}
