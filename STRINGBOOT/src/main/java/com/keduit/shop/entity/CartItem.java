package com.keduit.shop.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // JPA에서 엔티티로 매핑되는 클래스
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
@Table(name = "cart_item") // 데이터베이스 테이블 이름을 "cart_item"으로 설정
public class CartItem extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 사용자 ID를 자동 생성
  @Column(name = "cart_item_id") // 데이터베이스에서 사용할 컬럼 이름 지정
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) // 장바구니와 다대일 관계 설정 (여러 CartItem이 하나의 Cart에 속함)
  @JoinColumn(name = "cart_id") // 외래 키 이름을 "cart_id"로 설정
  private Cart cart; // 장바구니 정보

  @ManyToOne(fetch = FetchType.LAZY) // 아이템과 다대일 관계 설정 (여러 CartItem이 하나의 Item에 속함)
  @JoinColumn(name = "item_id") // 외래 키 이름을 "item_id"로 설정
  private Item item; // 아이템 정보

  private int count; // 장바구니에 담긴 아이템의 수량

  public static CartItem createCartItem(Cart cart, Item item, int count) {
    CartItem cartItem = new CartItem();
    cartItem.setCart(cart);
    cartItem.setItem(item);
    cartItem.setCount(count);
    return cartItem;
  }

  // 장바구니에 있는 상품을 추가로 장바구니에 넣으면 수량만 더해주는 메소드
  public void addCount(int count) {
    this.count += count;
  }

  public void updateCount(int count) {
    this.count = count;
  }
}

