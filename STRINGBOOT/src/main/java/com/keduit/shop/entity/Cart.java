package com.keduit.shop.entity;
//장바구니임

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity // JPA에서 엔티티로 매핑되는 클래스
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
public class Cart extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 사용자 ID를 자동 생성
  @Column(name = "cart_id") // 데이터베이스에서 사용할 컬럼 이름 지정
  private Long id;

  //@OneToOne: 회원 테이블과 1:1 매핑을 설정
  //@JoinColumn: 외래 키 지정, name 속성은 외래 키의 이름을 정의
  //@OneToOne(fetch=FetchType.EAGER): 패치 전략은 EAGER와 LAZY 중 선택 가능
  // EAGER: 즉시 로딩, LAZY: 지연 로딩
  @OneToOne(fetch = FetchType.LAZY) // 회원 정보를 지연 로딩으로 설정
  @JoinColumn(name = "member_id") // 외래 키 이름을 "member_id"로 설정
  private Member member; // 장바구니와 연결된 회원 정보

  public static Cart createCart(Member member) {
    Cart cart = new Cart();
    cart.setMember(member);
    return cart;
  }
}

