package com.keduit.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString // 객체의 문자열 표현을 자동으로 생성
public class OrderItem extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 주문 아이템 ID를 자동 생성
  @Column(name = "order_item_id") // 데이터베이스에서 사용할 컬럼 이름을 "order_item_id"로 설정
  private Long id; // 주문 아이템 ID

  @ManyToOne(fetch = FetchType.LAZY) // 여러 주문 아이템이 하나의 아이템에 속함 (다대일 관계)
  @JoinColumn(name = "item_id") // 외래 키 이름을 "item_id"로 설정
  private Item item; // 주문한 아이템 정보

  @ManyToOne(fetch = FetchType.LAZY) // 여러 주문 아이템이 하나의 주문에 속함 (다대일 관계)
  @JoinColumn(name = "order_id") // 외래 키 이름을 "order_id"로 설정
  private Order order; // 관련된 주문 정보

  private int orderPrice; // 주문 시 가격
  private int count; // 주문한 수량

  //장바구니
  public static OrderItem createOrderItem(Item item, int count) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setCount(count);
    orderItem.setOrderPrice(item.getPrice());

    item.removeStock(count);
    return orderItem;
  }

  public int getTotalPrice() {
    return orderPrice * count;
  }

  // 주문 취소시 주문 수량 만큼 상품의 재고를 더해 줌
  public void cancel() {
    this.getItem().addStock(count);
    
  }

  // private LocalDateTime regTime; // 등록 시간 (주석 처리)
  // private LocalDateTime updateTime; // 수정 시간 (주석 처리)
}
