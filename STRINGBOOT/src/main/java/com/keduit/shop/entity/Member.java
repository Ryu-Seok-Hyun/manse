package com.keduit.shop.entity;
//DTO작성 후에 왔음.

import com.keduit.shop.constant.Role;
import com.keduit.shop.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity // JPA에서 엔티티로 매핑되는 클래스
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
public class Member extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 사용자 ID를 자동 생성
  @Column(name = "member_id") // 데이터베이스에서 사용할 컬럼 이름을 "member_id"로 설정
  private Long id; // 회원 ID

  private String name; // 회원 이름

  @Column(unique = true) // 이메일은 고유해야 함
  private String email; // 회원 이메일

  private String password; // 회원 비밀번호

  private String address; // 회원 주소

  @Enumerated(EnumType.STRING) // 역할을 문자열로 저장
  private Role role; // 회원 역할 (예: USER, ADMIN)

  // MemberDTO를 바탕으로 Member 객체를 생성하는 정적 메서드
  public static Member createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
    Member member = new Member(); // 새로운 Member 객체 생성
    member.setName(memberDTO.getName()); // 이름 설정
    member.setEmail(memberDTO.getEmail()); // 이메일 설정
    member.setAddress(memberDTO.getAddress()); // 주소 설정
    String password = passwordEncoder.encode(memberDTO.getPassword()); // 비밀번호 암호화
    member.setPassword(password); // 암호화된 비밀번호 설정
    member.setRole(Role.USER); // 기본 역할을 USER로 설정 (ADMIN으로 변경 가능)
    return member; // 생성된 Member 객체 반환
  }
}
